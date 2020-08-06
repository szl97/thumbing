package com.thumbing.shared.annotation;

import lombok.NonNull;
import java.lang.annotation.*;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 16:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface AccessLock {
    @NonNull
    String value();
    long seconds() default 10l;
}
