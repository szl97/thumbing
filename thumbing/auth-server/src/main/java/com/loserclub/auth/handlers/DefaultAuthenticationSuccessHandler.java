package com.loserclub.auth.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserclub.shared.auth.model.UserContext;
import com.loserclub.shared.jwt.JwtTokenFactory;
import com.loserclub.shared.response.BaseApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 21:20
 */
@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private JwtTokenFactory jwtTokenFactory;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();
        String accessToken = jwtTokenFactory.createJwtToken(userContext);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        mapper.writeValue(httpServletResponse.getWriter(),  BaseApiResult.success(accessToken,"登录成功"));
        clearAuthenticationAttributes(httpServletRequest);
    }

    private  void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
