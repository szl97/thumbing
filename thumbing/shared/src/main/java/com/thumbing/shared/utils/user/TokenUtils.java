package com.thumbing.shared.utils.user;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.jwt.JwtTokenFactory;
import com.thumbing.shared.jwt.extractor.JwtHeaderTokenExtractor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 21:24
 */
@Component
@Slf4j
public class TokenUtils {
    private final String AUTHORIZATION_HEADER = "Authorization";
    @Autowired
    private JwtHeaderTokenExtractor jwtHeaderTokenExtractor;
    @Autowired
    private JwtTokenFactory jwtTokenFactory;
    public UserContext getUserContext(HttpServletRequest request){
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        return getUserContext(authorization);
    }

    public UserContext getUserContext(String authorization){
        UserContext userContext=null;
        if (!StringUtils.isBlank(authorization)) {
            userContext = jwtTokenFactory.parseJwtToken(jwtHeaderTokenExtractor.extract(authorization));
        }
        return userContext;
    }

    public Date getTokenExpireTime(String authorization){
        Date date = null;
        if(!StringUtils.isBlank(authorization)){
            date = jwtTokenFactory.parseTokenExpireTime(jwtHeaderTokenExtractor.extract(authorization));
        }
        return date;
    }

    public Date getTokenCreationTime(String authorization){
        Date date = null;
        if(!StringUtils.isBlank(authorization)){
            date = jwtTokenFactory.parseTokenCreationTime(jwtHeaderTokenExtractor.extract(authorization));
        }
        return date;
    }
}
