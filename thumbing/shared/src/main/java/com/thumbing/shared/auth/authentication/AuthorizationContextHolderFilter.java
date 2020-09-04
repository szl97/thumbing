package com.thumbing.shared.auth.authentication;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.jwt.JwtTokenFactory;
import com.thumbing.shared.utils.user.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 17:49
 */
@Component
public class AuthorizationContextHolderFilter extends GenericFilterBean {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String REFRESH_AUTHORIZATION_HEADER = "RefreshAuthorization";
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private JwtTokenFactory jwtTokenFactory;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        Date date = tokenUtils.getTokenExpireTime(authorization);
        Date compare = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant());
        if(compare.after(date)){
            UserContext userContext = tokenUtils.getUserContext(authorization);
            String refreshAuthorization = jwtTokenFactory.createJwtToken(userContext);
            if(refreshAuthorization != null){
                authorization = refreshAuthorization;
            }
            response.setHeader(REFRESH_AUTHORIZATION_HEADER, authorization);
        }
        AuthorizationContextHolder.setAuthorization(authorization);
        filterChain.doFilter(request, response);
        AuthorizationContextHolder.clear();
    }
}
