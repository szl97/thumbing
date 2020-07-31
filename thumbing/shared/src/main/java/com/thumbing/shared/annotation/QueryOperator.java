package com.thumbing.shared.annotation;

import com.thumbing.shared.jpa.Operator;

import java.lang.annotation.*;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryOperator {
    Operator value() default Operator.EQ;

    String fieldName() default "";
}