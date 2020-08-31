package com.thumbing.usermanagement.context;

import com.thumbing.shared.auth.model.UserContext;
import lombok.experimental.UtilityClass;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/31 10:50
 */
@UtilityClass
public class UserContextHolder {
    private static ThreadLocal<UserContext> THREAD_LOCAL_LOGIN_USER_NAME= new ThreadLocal<>();

    public UserContext getUser() {
        return THREAD_LOCAL_LOGIN_USER_NAME.get();
    }

    public void setUser(UserContext userContext) {
        THREAD_LOCAL_LOGIN_USER_NAME.set(userContext);
    }

    public void clear() {
        THREAD_LOCAL_LOGIN_USER_NAME.remove();
    }
}
