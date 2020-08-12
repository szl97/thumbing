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
     * list
     * 每个会话中保存50条消息
     * id1:id2
     */
    public static final String CHAT_RECORD = "CHAT:RECORD";

}
