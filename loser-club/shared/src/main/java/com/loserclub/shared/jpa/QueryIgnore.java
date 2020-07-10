package com.loserclub.shared.jpa;

import java.lang.annotation.*;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryIgnore {
}