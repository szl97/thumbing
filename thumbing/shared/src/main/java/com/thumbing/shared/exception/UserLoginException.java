package com.thumbing.shared.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 21:22
 */
public class UserLoginException extends AuthenticationException {
    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    private int code;

    public UserLoginException(String msg) {
        super(msg);
    }

    public int getCode(){
        return code;
    }
    public void setCode(int c){
        code = c;
    }
}
