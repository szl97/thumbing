# 				Thumbing App 主要功能开发文档

登录验证模块，web socket模块，内容模块，好友模块，聊天模块，聊天室模块

## 1 登录验证模块

### 概述

登录验证模块包含用户注册、登录、以及客户端请求的认证功能等。即Auth-Server模块。

### 功能详解

#### 注册

客户端需设置账号问题，作为更改密码的依据之一。

服务端验证用户名的唯一性，客户端验证密码的合法性。提供手机和微信注册通道。验证微信账号和手机号的唯一性。注册后可以绑定微信和手机号。

#### 登录

保存登录的设备信息，新设备登录，若绑定手机号则需要认证。对于客户端，保存所有登录过的账号密码（可下一阶段实现），进入APP后，自动登录上一次成功登录的账号。若出现顶号情况，对于绑定手机用户，需要手机认证才能成功登录。

登录成功后，服务端返回jwt生成的token作为之后web请求的请求头。

连续登录失败5次，冻结1小时，更改密码后可以解锁登录。

#### 实现成功登录后下一次进入自动登录的方法

登录成功后，客户端保存token和用户名密码到本地。下一次进入直接用token请求资源，如果web验证不通过（见下文）则说明token过期，自动使用本地存储的用户名和密码重新登录。不直接重新登录的目的是为了缓解服务端压力，app端的使用情况是经常频道退出打开，而用户密码不宜存储在redis中，登录请求必须请求数据库，频繁登录会造成频繁的数据库请求。

#### 客户端请求认证

客户端所有的web请求都经过服务端的网关转发到各个服务，在网关处调用auth-server服务的请求认证，认证请求头中的token。

#### 修改密码

修改密码途径有二：手机验证修改以及密码问题修改

### 数据库设计

主要有sys_user，device两张主表构成此模块。

#### 注意区分sys_user和user_info表

user_info和sys_user中的都有user_name索引作为唯一索引。但是不做关联。

user_info主要服务于好友关系这些功能，而sys_user服务于登录权限功能。他们可以独立部署，不作关联是为了降低服务之间的耦合度。

### Redis保存什么

主要存储类型为String类型数据。初步设想包括user登录后的token，手机验证码，登录失败次数。

## 2 Web Socket模块(Push Data)

### 概述

Web Socket模块建立和维持服务端与客户端之间的长连接，实现服务端消息主的动推送。

### 模块的子服务

在structure文件夹下的“消息系统架构初步设计.jpg”中有比较详细的解释。但是他们与其他模块的关系没有说明（当时还没想完善又懒得重写，而且里面关于data-center直接写入数据库的描述并不正确，是调用对应的写入数据库的服务进行写入）。在此主要简述，此模块中的子服务和其他服务之间的关系，他们和客户端之间的关系，在jpg中没有讲述的如何加密，以及某些比较核心的问题。

此模块即push-data模块，push-data模块由三部分组成，common，data-center和node-server。common是一个公共包，只是放data-center和node-server的共有代码，提高代码复用率和可维护性。

### node-server简介

node-server只负责和客户端建立socket连接，是客户端和服务端之间socket通信的最直接处理人。所有经由socket连接发送的消息都由node-server直接进行发送任务和接收任务。在系统内部，只有data-center对node-server可见，noder-server之间以及noder-server与其他服务之间皆不可见，具体原因下述。

### 为什么要有data-center

node-server已经足够处理socket连接下的消息推送和收取。但是如果用户增长，需要的node-server服务增加，不同的客户端可能连接了不同的node-server。服务器要向客户端推送消息的时候，并不知道该用户的消息应该由哪个node-server去推送。当然，有一种做法是，直接让每个node-server直接建立连接，且在他们内部都存储一份每个客户端具体连接到了哪个node-server的路由表，任何node-server获取推送任务后，都可以通过路由表，将消息转发给那个可以与该客户端进行socket通信的node-server。但如此做，势必造成一台node-server可以建立的客户端连接数下降以及系统的内部服务之间的"协调管理"看上去很混乱的问题。因此引入了一个data-center的概念，对node-server进行统一的管理。

### data-center的功能详解

data-center是消息推送路线上的“交通枢纽”，是系统内部服务和noder-server之间的枢纽以及noder-server之间的枢纽。而客户端之间的通信的途径有二，发送方socket 连接发送+接受方socket连接接收，以及发送方web请求发送+接受方socket连接接收，换成服务言之就是node server+node server或其他web服务+node server，也就是说无论客户端之间如何通信，必然要经过data-center。

data-center暴露内部可调用服务。系统内的任何服务想要通过socket实现消息推送，必须调用data-center暴露的接口或发送到data-center中消息队列服务监听的队列，在data-center中找到并将推送消息通过data-center和node-server直接的sokcet连接发送给对应的node-server。

node-server直接通过和data-center的socket连接告知data-center需要推送的消息。

### 如何确保data-center和每个node-server都建立了连接，并且知道对应的客户端连接了哪个node-server

主要是利用zookeeper的强一致性，每个data-center和node-server的启动都要在zookeeper上注册，data-center和node-server之间互相发现对方，并建立socket连接。客户端与node-server建立socket连接的第一步，必须向data-center发送请求，去找到那个最空闲的node-server，也就是说任何没有成功注册并建立连接的node-server是无法对外提供任何服务的。客户端和node-server建立每一个连接并握手请求成功后，node-server也都会通过socket连接通知data-center，由data-center保存路由信息（效率和准确性待考验，如果效果不好，就直接在redis上存储路由信息）。

### Socket连接中的加密

使用netty框架。加密部分仿照https的加密进行设计。建立连接后，服务端发送RSA公钥，客户端生成AES密钥，使用RSA公钥加密后，放入传输体中，并用AES密钥加密token放入传输体中，发送给node-server，即发送握手请求。node-server收到后，用RSA私钥解密密钥的密文，拿到AES密钥，并用AES密钥解密token的密文，拿到token，并通过data-center调用auth-server服务验证token，将验证结果通知node-server，失败则断开连接，成功则在对应的连接通道（channel）的属性中添加握手标识，AES密钥，用户ID以及设备ID。之后客户端发送的消息都通过这个AES密钥进行加密，服务端获取channel中设置的属性值进行解密，推送消息时，同样在属性值中获取密钥进行加密。

## 3 内容模块

### 概述

内容模块包括帖子，文章和心情吐槽。客户端展现上，帖子和文章显示于首页，心情吐槽显示在"港湾"页面。

### 主要功能

#### 获取内容

按时间获取和按照推荐获取。暂时只实现按照时间获取。

#### 发表内容

发表文章和帖子需设置标签，标签可以自定义，最多5个。文章和帖子的前100字作为abstract。首页列表显示的是abstracts。

#### 搜索内容

文章和帖子可以搜索，搜索主要匹配的是文章的标题、标签、abstracts，以及帖子的标签和内容

#### 发表评论

#### 点赞

### 功能实现

#### 数据库和搜索引擎

文档形式数据，使用mongodb存储。搜索引擎使用elsticsearch 7。

#### 在Redis中拉取最新内容和最新评论

内容和评论都在redis中以list类型存储，首页拉取可显示一个月内的内容。且新添加的数据，在尾部添加。因为app端获取更多，相当于分页查询，拉取新的一页。做法是在list中在上次的位置上拉取下一页数据。如果在头部添加数据，那么之前数据的位置也发生改变，将无法定位。刷新操作会触发重新拉取，获取最新添加的数据。

#### 搜索内容的存储

添加时将文章的标题标签简述和帖子的标签内容简述存入elasticsearch中，搜索关键字高亮显示。

#### 点赞的处理

实体中定义set类型。在redis中以set类型存储点赞者ID。

#### Redis如何设置Key

hash类型存储每个内容和评论。时间排序的存储使用list存储id。热度排序使用Zset存储id。

#### 评论的存储

评论作为一个单独的表，记录所属内容的，和所属的评论id，为两层嵌套形式，所有子评论都是第一级评论的下一层。

#### 评论的处理

```
/**
 * 文章下匿名评论的昵称 key+article的主键
 * hash
 * 存储NICK NAME的Sequence 的 key: nick_name_sequence
 * 存储THUMBING_NUM 的 key: thumbing_num
 * 存储COMMENTS_NUM 的 key: comments_num
 * 存储详情的key: details （不包括内容和abstracts）
 * 存储内容的abstracts: abstracts 存储abstracts列表显示
 * 存储内容的key: content 存储内容打开详情页后显示
 * 过期时间30天，每次修改更新过期时间
 */
public static final String INFO_ARTICLE = "INFO:ARTICLE:";
/**
 * 记录发生改变的文章的ID
 * 分别是点赞数，评论数和内容
 * 其中内容改变需要要修改content和abstract
 * 文章发生对应的改变后加入对应的list
 * 定时任务每分钟将发生的变化写入Mongo中
 * 并从redis中删除元素
 * 因此先把所有元素pop到一个对应的list中，然后根据这个list修改数据库
 */
public static final String ARTICLE_CHANGED_THUMBING_NUM = "ARTICLE:CHANGED:THUMBING_NUM";
public static final String ARTICLE_CHANGED_COMMENTS_NUM = "ARTICLE:CHANGED:THUMBING:COMMENTS_NUM";
public static final String ARTICLE_CHANGED_CONTENT = "ARTICLE:CHANGED:CONTENT";
/**
 * 帖子下匿名评论的昵称 key+moments的主键
 * hash
 * 存储NICK NAME的Sequence 的 key: nick_name_sequence
 * 存储THUMBING_NUM 的 key: thumbing_num
 * 存储COMMENTS_NUM 的 key: comments_num
 * 存储详情的key: details 列表显示（不包括内容）
 * 存储内容的key: content 存储内容打开详情页后显示
 * 过期时间30天，每次修改更新过期时间
 */
public static final String INFO_MOMENTS = "INFO:MOMENTS:";
/**
 * 记录发生改变的帖子的ID
 * 分别是点赞数，评论数和内容
 * 帖子发生对应的改变后加入对应的list
 * 定时任务每分钟将发生的变化写入Mongo中
 * 并从redis中删除元素
 * 因此先把所有元素pop到一个对应的list中，然后根据这个list修改数据库
 */
public static final String MOMENTS_CHANGED_THUMBING_NUM = "MOMENTS:CHANGED:THUMBING_NUM";
public static final String MOMENTS_CHANGED_COMMENTS_NUM = "MOMENTS:CHANGED:THUMBING:COMMENTS_NUM";
public static final String MOMENTS_CHANGED_CONTENT = "MOMENTS:CHANGED:CONTENT";
/**
 * 文章下的评论列表 key+article的主键
 * 只存储父评论的ID
 * list
 * 过期时间30天，每次修改更新过期时间
 */
public static final String COMMENTS_ARTICLES = "COMMENTS:CHANGED:";
/**
 * 帖子下的评论列表 key+moments的主键
 * 只存储父评论的ID
 * list
 * 过期时间30天，每次修改更新过期时间
 */
public static final String COMMENTS_MOMENTS = "COMMENTS:MOMENTS";
/**
 * 存储评论下的子评论Id
 * key+父评论的CommentsId
 * list
 * 过期时间30天，每次修改更新过期时间
 */
public static final String CHILD_COMMENTS = "CHILD:COMMENTS:";
/**
 * 存储被删除的评论的CommentsId
 * 删除后CommentsId加入list
 * 定时任务每分钟将删除的评论写入Mongo中
 * 并从redis中删除元素
 * 因此先把所有元素pop到一个list中，然后根据这个list修改数据库
 * list
 */
public static final String COMMENTS_CHANGED = "COMMENTS:CHANGED";
/**
 * 存储点赞数发生变化的评论的CommentsId
 * 点赞数发生变化CommentsId后加入list
 * 定时任务每分钟将点赞数变化写入Mongo中
 * 并从redis中删除元素
 * 因此先把所有元素pop到一个list中，然后根据这个list修改数据库
 * list
 */
public static final String COMMENTS_THUMBING_CHANGED = "COMMENTS:THUMBING:CHANGED";
/**
 * 评论的详情 key+CommentsId
 * hash
 * key: [details,thumbing_num]
 * 过期时间30天，每次修改更新过期时间，并且加入到对应的CHANGED list中
 */
public static final String COMMENTS_DETAILS = "COMMENTS:DETAILS:";
```

#### 点赞和评论的发送

web请求发送点赞和评论请求，通过socket连接推送给要通知的人。

#### 图片如何处理

上传图片到OSS，文档中的图片对应位置存储图片url。

## 4 好友模块

### 主要功能

#### 添加好友

web请求发送添加申请，socket连接向被添加者发送消息。

#### 删除好友

#### 拉黑

#### 获取好友列表

### 数据库设计

mySql数据库。user_info中建立关联关系。

## 5 聊天模块

### 主要功能

#### 发送消息

通过socket连接，验证好友关系后可以发送。并生成聊天记录，并添加或更改相应的会话。

#### 获取会话列表

进入消息界面，获取最近的消息列表，包含最后一条消息和是否已读。

#### 获取消息记录

进入聊天界面，获取和对应好友的聊天记录。

### Redis存储聊天记录

redis存储每两个用户之间的最近200条聊天记录。

### 处理聊天消息的过程

#### 单聊聊天

node server收到聊天消息，生成唯一的消息序列号，通过与data center的交互找到连接的同时，发送消息到消息队列，接受者进行数据库写入操作。如果用户已读消息，则请求已读消息API，如果此时消息还未被写入，则直接写入。因此这个操作需要分布式锁。目的是为了防止两次相同的写入。为了防止聊天过程中写入过于频繁，客户端可以积累一定数量的聊天记录后或在用户退出聊天窗口时触发服务端的已读请求。 

#### 群聊消息

node server收到聊天消息，若为群聊，写入redis中，并且发送给所有用户。群聊消息记录在群组结束之时，统一写入mongo。群聊的redis存储为群id-消息id，消息id-消息列表。

### 如果没有找到对应用户的channel连接怎么办

data center将消息发送到所有的node server，每个node server都查找一次，如果依然找不到，则认为用户并没与服务器建立连接。此目的是为了解决客户端信息同步的时延问题。

## 6 聊天室模块



