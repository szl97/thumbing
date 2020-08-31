package com.thumbing.usermanagement.controller.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/31 14:56
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    UserNameInterceptor userNameInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userNameInterceptor).addPathPatterns("/personal/userInfo/register");
    }
}
