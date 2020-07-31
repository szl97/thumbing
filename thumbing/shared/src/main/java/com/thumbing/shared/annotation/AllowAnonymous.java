package com.thumbing.shared.annotation;

import java.lang.annotation.*;

/**
 * 允许匿名访问
 * @Author: Stan Sai
 * @Date: 2020/7/13 18:45
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AllowAnonymous {
}
