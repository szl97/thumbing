package com.thumbing.usermanagement.controller.Interceptor;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.Nullable;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.constants.HttpHeaderConstants;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.usermanagement.context.UserContextHolder;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/31 11:22
 */
@Component
public class UserNameInterceptor extends HandlerInterceptorAdapter {
    private final String USERNAME_HEADER = HttpHeaderConstants.USERNAME_HEADER;
    @Value("${rsa.password}")
    private String password;
    @Value("${rsa.publicKey}")
    private String publicKey;
    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        String encryptStr = request.getHeader(USERNAME_HEADER);
        RSA rsa = new RSA(null, publicKey);
        byte[] decrypt = rsa.decrypt(encryptStr, KeyType.PublicKey);
        String decryptStr = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
        if(!decryptStr.substring(0,32).equals(password)) throw new BusinessException("请求认证失败");
        String userStr = decryptStr.substring(32);
        if(StringUtils.isBlank(userStr)) throw new BusinessException("请求头错误");
        UserContext userContext = objectMapper.readValue(userStr, UserContext.class);
        if(userContext == null || userContext.getId() == null || StringUtils.isBlank(userContext.getName())) throw new BusinessException("请求头错误");
        UserContextHolder.setUser(userContext);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) {
        UserContextHolder.clear();
    }

}
