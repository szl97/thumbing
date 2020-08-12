package com.thumbing.shared.annotation;

import java.lang.annotation.*;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 16:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface AccessLock {
    String value();
    String className() default "";
    String[] fields() default {};
    long seconds() default 10l;
}
