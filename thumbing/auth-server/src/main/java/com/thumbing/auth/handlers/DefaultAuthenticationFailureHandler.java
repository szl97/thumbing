package com.thumbing.auth.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbing.auth.context.LoginRequestContextHolder;
import com.thumbing.auth.dto.input.LoginRequest;
import com.thumbing.auth.service.IAuthService;
import com.thumbing.shared.exception.AccountLockException;
import com.thumbing.shared.exception.UserLoginException;
import com.thumbing.shared.jwt.exception.JwtExpiredTokenException;
import com.thumbing.shared.response.BaseApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 21:21
 */
@Component
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private IAuthService authService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            mapper.writeValue(httpServletResponse.getWriter(), BaseApiResult.errorServer(e.getMessage()));
        } else if (e instanceof JwtExpiredTokenException) {
            mapper.writeValue(httpServletResponse.getWriter(), BaseApiResult.errorServer("登陆已过期，请重新登陆"));
        } else if(e instanceof UserLoginException){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            LoginRequest loginRequest = LoginRequestContextHolder.getLoginRequest();
            authService.failLogin(loginRequest);
            BaseApiResult result = new BaseApiResult(((UserLoginException) e).getCode(),e.getMessage(),null,true);
            mapper.writeValue(httpServletResponse.getWriter(), result);
        } else if(e instanceof AccountLockException){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            BaseApiResult result = new BaseApiResult(((UserLoginException) e).getCode(),e.getMessage(),null,true);
            mapper.writeValue(httpServletResponse.getWriter(), result);
        }
        //TODO:细化错误返回
        mapper.writeValue(httpServletResponse.getWriter(),  BaseApiResult.errorServer("无效访问："+e.getMessage()));
        LoginRequestContextHolder.clear();
    }
}
