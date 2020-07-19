package com.loserclub.shared.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 19:03
 */
public class RedisCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //todo：是否可以通过注入得方式从appConfig里读取
        String result = conditionContext.getEnvironment().getProperty("use.redis");
        return "true".equalsIgnoreCase(result);
    }
}
