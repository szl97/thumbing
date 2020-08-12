package com.thumbing.auth.context;

import com.thumbing.auth.dto.input.LoginRequest;
import com.thumbing.shared.entity.sql.system.User;
import lombok.experimental.UtilityClass;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 15:13
 */
@UtilityClass
public class LoginUserContextHolder {
    private static ThreadLocal<User> THREAD_LOCAL_LOGIN_USER= new ThreadLocal<>();

    public User getLoginUser() {
        return THREAD_LOCAL_LOGIN_USER.get();
    }

    public void setLoginUser(User user) {
        THREAD_LOCAL_LOGIN_USER.set(user);
    }

    public void clear() {
        THREAD_LOCAL_LOGIN_USER.remove();
    }
}
