package com.loserclub.shared.auth.authentication;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 17:49
 */
@Component
public class AuthorizationContextHolderFilter extends GenericFilterBean {

    private final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        AuthorizationContextHolder.setAuthorization(authorization);
        filterChain.doFilter(request, response);
        AuthorizationContextHolder.clear();
    }
}
