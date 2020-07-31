package com.thumbing.shared.utils.security;

import com.thumbing.shared.auth.authentication.AuthorizationContextHolder;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.utils.user.UserContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 21:25
 */
@Component
public class SecurityUtils {
    @Autowired
    UserContextUtils userContextUtils;
    private final static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    public UserContext getCurrentUser() {
        String authorization = AuthorizationContextHolder.getAuthorization();
        if (!StringUtils.isEmpty(authorization)) {
            return userContextUtils.getUserContext(authorization);
        }
        return null;
    }
}