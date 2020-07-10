package com.loserclub.shared.jpa;

import java.lang.annotation.*;

/**
 * 自定义逻辑删除注解
 *
 * @author Stan Sai
 * @date 2020-06-28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface LogicDelete {
}