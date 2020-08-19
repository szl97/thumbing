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
     * list 保存100条消息
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
}
