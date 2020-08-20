package com.thumbing.shared.constants;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 15:46
 */
public class CacheKeyConstants {
    /**
     * hash
     * 需要权限访问的url和对应的所需访问权限
     * application name
     */
    public static final String AUTH_PERMISSION_URL_KEY = "AUTH:PERMISSION:URL:APP:";
    /**
     * Set
     * 可以匿名访问的url
     * application name
     */
    public static final String AUTH_PERMISSION_ANONYMOUS_KEY = "AUTH:PERMISSION:ANONYMOUS:APP:";
    /**
     * String
     * 用户的Token
     * userName
     */
    public static final String TOKEN = "TOKEN:USER_NAME:";
    /**
     * String
     * 用户的失败登录次数
     * userName
     */
    public static final String FAILURE_LOGIN = "FAILURE_LOGIN:USER:";
    /**
     * String
     * 注册的验证码
     * phoneNumber/email
     */
    public static final String VALIDATION_FOR_REGISTER = "VALIDATION:REGISTER:";
    /**
     * String
     * 修改密码的验证码
     * userName
     */
    public static final String VALIDATION_FOR_CHANGE_PASSWORD = "VALIDATION:PASSWORD_CHANGE:";
    /**
     * String
     * 缓存所有的job occupation 和 interest
     * com.thumbing.usermanagement.dto.output.PersonalConfigurationDto
     */
    public static final String PERSONAL_CONFIGURATION = "PERSONAL:CONFIGURATION";
    /**
     * list 保存1000条消息
     * id1:id2
     * 过期时间30天
     */
    public static final String CHAT_RECORD = "CHAT:RECORD:";
    /**
     * hash
     * id
     * targetId
     * 过期时间30天
     */
    public static final String SESSION_RECORD = "SESSION:RECORD:";
    /**
     * 文章列表 保存Id
     * 保存最近的10000篇记录
     * list
     */
    public static final String ARTICLE_LIST = "ARTICLE:LIST";
    /**
     * 帖子列表 保存Id
     * 保存最近的50000条记录
     * list
     */
    public static final String MOMENTS_LIST = "MOMENTS:LIST";
    /**
     * 文章下匿名评论的昵称 key+article的主键
     * hash
     * 存储NICK NAME的Sequence 的 key: nick_name_sequence int
     * 存储THUMBING_NUM 的 key: thumbing_num int
     * 存储COMMENTS_NUM 的 key: comments_num int
     * 存储详情的key: details （不包括内容和abstracts）entity
     * 存储内容的abstracts: abstracts 存储abstracts列表显示 string
     * 存储内容的key: content 存储内容打开详情页后显示 string
     * 过期时间30天，每次修改更新过期时间
     */
    public static final String INFO_ARTICLE = "INFO:ARTICLE:";
    public static final String NICK_NAME_SEQUENCE = "nick_name_sequence";
    public static final String THUMBING_NUM = "thumbing_num";
    public static final String COMMENTS_NUM = "comments_num";
    public static final String DETAILS = "details";
    public static final String ABSTRACTS = "abstracts";
    public static final String CONTENT = "content";
    /**
     * 文章的点赞用户集合
     * set
     * 存储userId(Long)
     */
    public static final String ARTICLE_THUMBING_USER_SET = "ARTICLE:THUMBING:USER_Id:SET:";
    /**
     * 记录发生改变的文章的ID
     * Set
     * 分别是点赞数，评论数和内容
     * 其中内容改变需要要修改content和abstract
     * 文章发生对应的改变后加入对应的Set
     * 定时任务每分钟将发生的变化写入Mongo中
     * 并从redis中删除元素
     * 因此先把所有元素pop到一个对应的list中，然后根据这个list修改数据库
     */
    public static final String ARTICLE_CHANGED_THUMBING_NUM = "ARTICLE:CHANGED:THUMBING_NUM";
    public static final String ARTICLE_CHANGED_COMMENTS_NUM = "ARTICLE:CHANGED:THUMBING:COMMENTS_NUM";
    public static final String ARTICLE_CHANGED_CONTENT = "ARTICLE:CHANGED:CONTENT";
    /**
     * 代表使用的changed set的序号
     */
    public static final String ARTICLE_CHANGED_THUMBING_SEQ = "ARTICLE:CHANGED:THUMBING:SEQ";
    public static final String ARTICLE_CHANGED_COMMENTS_SEQ = "ARTICLE:CHANGED:COMMENTS:SEQ";
    public static final String ARTICLE_CHANGED_CONTENT_SEQ = "ARTICLE:CHANGED:CONTENT:SEQ";
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
     * 帖子的点赞用户集合
     * set
     * 存储userId(Long)
     */
    public static final String MOMENTS_THUMBING_USER_SET = "MOMENTS:THUMBING:USER_Id:SET:";
    /**
     * 记录发生改变的帖子的ID(string)
     * 分别是点赞数，评论数和内容
     * Set
     * 帖子发生对应的改变后加入对应的Set
     * 定时任务每分钟将发生的变化写入Mongo中
     * 并从redis中删除元素
     * 因此先把所有元素pop到一个对应的list中，然后根据这个list修改数据库
     */
    public static final String MOMENTS_CHANGED_THUMBING_NUM = "MOMENTS:CHANGED:THUMBING_NUM";
    public static final String MOMENTS_CHANGED_COMMENTS_NUM = "MOMENTS:CHANGED:THUMBING:COMMENTS_NUM";
    public static final String MOMENTS_CHANGED_CONTENT = "MOMENTS:CHANGED:CONTENT";
    /**
     * 代表使用的changed set的序号
     */
    public static final String MOMENTS_CHANGED_THUMBING_SEQ = "MOMENTS:CHANGED:THUMBING:SEQ";
    public static final String MOMENTS_CHANGED_COMMENTS_SEQ = "MOMENTS:CHANGED:COMMENTS:SEQ";
    public static final String MOMENTS_CHANGED_CONTENT_SEQ = "MOMENTS:CHANGED:CONTENT:SEQ";
    /**
     * 文章下的评论列表 key+article的主键
     * 只存储父评论的commentID(Long)
     * list
     * 过期时间30天，每次修改更新过期时间
     */
    public static final String COMMENTS_ARTICLES = "COMMENTS:CHANGED:";
    /**
     * 帖子下的评论列表 key+moments的主键
     * 只存储父评论的commentID(Long)
     * list
     * 过期时间30天，每次修改更新过期时间
     */
    public static final String COMMENTS_MOMENTS = "COMMENTS:MOMENTS";
    /**
     * 存储评论下的子评论Id(Long)
     * key+父评论的CommentsId
     * list
     * 过期时间30天，每次修改更新过期时间
     */
    public static final String CHILD_COMMENTS = "CHILD:COMMENTS:";
    /**
     * 存储被删除的评论的CommentsId(Long)
     * Set
     * 删除后CommentsId加入Set
     * 定时任务每分钟将删除的评论写入Mongo中
     * 并从redis中删除元素
     * 因此先把所有元素pop到一个list中，然后根据这个list修改数据库
     * list
     */
    public static final String COMMENTS_DELETED = "COMMENTS:DELETED";
    /**
     * 存储点赞数发生变化的评论的CommentsId(Long)
     * Set
     * 点赞数发生变化CommentsId后加入Set
     * 定时任务每分钟将点赞数变化写入Mongo中
     * 并从redis中删除元素
     * 因此先把所有元素pop到一个list中，然后根据这个list修改数据库
     * list
     */
    public static final String COMMENTS_THUMBING_CHANGED = "COMMENTS:THUMBING:CHANGED";
    /**
     * 代表使用的changed set的序号
     */
    public static final String COMMENTS_DELETED_SEQ = "COMMENTS:DELETED:SEQ";
    public static final String COMMENTS_CHANGED_THUMBING_SEQ = "COMMENTS:CHANGED:THUMBING:SEQ";
    /**
     * 评论的详情 key+CommentsId
     * hash
     * key: [details,thumbing_num] (entity, int)
     * 过期时间30天，每次修改更新过期时间，并且加入到对应的CHANGED list中
     */
    public static final String COMMENTS_DETAILS = "COMMENTS:DETAILS:";
    /**
     * 评论的点赞用户集合
     * set
     * 存储userId(Long)
     */
    public static final String COMMENTS_THUMBING_USER_SET = "COMMENTS:THUMBING:USER_Id:SET:";
    /**
     * 存储匿名评论时生成的nickName string
     * list
     */
    public static final String NICK_NAME_LIST = "NICK:NAME:LIST";
    /**
     * key+article主键
     * hash
     * hash key: userId
     * 存储用户在该文章下对应的昵称
     * 过期时间30天
     */
    public static final String USER_NICK_NAME_ARTICLE = "USER:NICK:NAME:ARTICLE:";
    /**
     * key+moments主键
     * hash
     * hash key: userId
     * 存储用户在该文章下对应的昵称
     * 过期时间30天
     */
    public static final String USER_NICK_NAME_MOMENTS = "USER:NICK:NAME:MOMENTS:";
}
