package com.thumbing.auth.context;

import com.thumbing.auth.dto.input.LoginRequest;
import lombok.experimental.UtilityClass;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 17:58
 */
@UtilityClass
public class LoginRequestContextHolder {

    private static ThreadLocal<LoginRequest> THREAD_LOCAL_LOGIN_REQUEST = new ThreadLocal<>();

    public LoginRequest getLoginRequest() {
        return THREAD_LOCAL_LOGIN_REQUEST.get();
    }

    public void setLoginRequest(LoginRequest loginRequest) {

        THREAD_LOCAL_LOGIN_REQUEST.set(loginRequest);
    }


    public void clear() {
        THREAD_LOCAL_LOGIN_REQUEST.remove();
    }
}
