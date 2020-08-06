package com.thumbing.shared.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.response.BaseApiResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 14:23
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return filter(methodParameter);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        // o is null -> return response
        if (body == null) {
            return BaseApiResult.success();
        }
        // string 特殊处理
        if (body instanceof String) {
            return  objectMapper.writeValueAsString(BaseApiResult.success(body));
        }
        return BaseApiResult.success(body);
    }

    private boolean filter(MethodParameter methodParameter) {
        Class<?> declaringClass = methodParameter.getDeclaringClass();

        // 只处理标注了该注解的controller
        // 检查注解是否存在
        if (methodParameter.getDeclaringClass().isAnnotationPresent(EnableResponseAdvice.class)) {
            return true;
        }
        if (methodParameter.getMethod().isAnnotationPresent(EnableResponseAdvice.class)) {
            return true;
        }
        return false;
    }
}
