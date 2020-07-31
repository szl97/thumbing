package com.thumbing.shared.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，是否不使用全局返回包装
 * @Author: Stan Sai
 * @Date: 2020/7/14 14:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface IgnoreResponseAdvice {
}
