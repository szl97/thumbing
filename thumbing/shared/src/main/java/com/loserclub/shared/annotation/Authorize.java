package com.loserclub.shared.annotation;

import java.lang.annotation.*;

/**
 * 自定义权限头
 * @Author: Stan Sai
 * @Date: 2020/7/13 18:23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Authorize {
    String value() default "";
}
