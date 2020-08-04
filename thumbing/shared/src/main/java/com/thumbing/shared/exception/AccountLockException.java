package com.thumbing.shared.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 20:23
 */
public class AccountLockException extends AuthenticationException {
    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public int code;

    public AccountLockException(String msg) {
        super(msg);
    }

    public int getCode(){
        return code;
    }
    public void setCode(int c){
        code = c;
    }
}
