package com.loserclub.shared.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 21:12
 */
public class JwtExpiredTokenException extends AuthenticationException {

    public JwtExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
