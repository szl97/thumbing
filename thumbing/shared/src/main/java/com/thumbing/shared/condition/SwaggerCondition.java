package com.thumbing.shared.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 20:20
 */
public class SwaggerCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //todo：是否可以通过注入得方式从appConfig里读取
        String result = context.getEnvironment().getProperty("use.swagger");
        return "true".equalsIgnoreCase(result);
    }
}
