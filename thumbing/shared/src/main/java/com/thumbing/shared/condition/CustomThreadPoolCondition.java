package com.thumbing.shared.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Stan Sai
 * @date 2020-08-06 22:43
 */
public class CustomThreadPoolCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //todo：是否可以通过注入得方式从appConfig里读取
        String result = context.getEnvironment().getProperty("use.customThreadPool");
        return "true".equalsIgnoreCase(result);
    }
}
