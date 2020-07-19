package com.loserclub.shared.config;

import com.loserclub.shared.condition.FeignConditional;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 8:54
 */
@Conditional(FeignConditional.class)
@Configuration
@Slf4j
public class FeignConfiguration implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            // 提取请求头中的Authorization，用于feign中添加请求头Authorization
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("Authorization");
            template.header("Authorization", token);
            // 注：由于feign调用的特殊性get
            // 使用dto的，feign请求时会以application/json;charset=UTF-8方式请求。所以请求方法使用post
            // 使用普通param得，feign请求时会以application/x-www-form-urlencoded;charset=UTF-8方式请求。所以请求方法使用get
        }
        log.info("feign interceptor header:{}", template);
    }
}
