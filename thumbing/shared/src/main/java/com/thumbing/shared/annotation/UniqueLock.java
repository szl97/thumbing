package com.thumbing.shared.annotation;

import lombok.NonNull;

import java.lang.annotation.*;

/**
 * 用于写入数据库的唯一值时加分布式锁，使用redis实现
 * className是方法参数中需要验证唯一性的完整类名
 * methods是类中获取唯一性参数值的get方法，支持多个
 * seconds是lock的过期时间，单位为 s，默认为 30s
 * @author Stan Sai
 * @date 2020-08-05 19:17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface UniqueLock {
    @NonNull
    String className();
    @NonNull
    String[] methods();
    long seconds() default 30l;
}
