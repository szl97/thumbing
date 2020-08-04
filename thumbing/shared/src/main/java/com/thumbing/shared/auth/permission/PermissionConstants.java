package com.thumbing.shared.auth.permission;

import lombok.Data;
import lombok.experimental.UtilityClass;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 18:01
 */
@UtilityClass
public class PermissionConstants {
    /**
     * 通过认证后拥有的权限
     * 对应sys_user中的access
     */
    public final static String ACCESS = "access";
    /**
     * 通过手机激活后用有的权限
     * 对应sys_user中的active
     */
    public final static String REGISTER = "register";
}
