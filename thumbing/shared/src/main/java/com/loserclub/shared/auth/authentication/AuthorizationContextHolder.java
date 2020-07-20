package com.loserclub.shared.auth.authentication;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 17:49
 */
@UtilityClass
@Slf4j
public class AuthorizationContextHolder {
    private static ThreadLocal<String> THREAD_LOCAL_AUTHORIZATION = new ThreadLocal<>();

    public String getAuthorization() {
        return THREAD_LOCAL_AUTHORIZATION.get();
    }

    public void setAuthorization(String authorization) {
        THREAD_LOCAL_AUTHORIZATION.set(authorization);
    }


    public void clear() {
        THREAD_LOCAL_AUTHORIZATION.remove();
    }
}
