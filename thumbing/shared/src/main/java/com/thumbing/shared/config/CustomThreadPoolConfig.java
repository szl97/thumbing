package com.thumbing.shared.config;

import com.thumbing.shared.condition.CustomThreadPoolCondition;
import com.thumbing.shared.thread.CustomThreadPool;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author Stan Sai
 * @date 2020-08-06 22:45
 */
@Conditional(CustomThreadPoolCondition.class)
@Configuration
@ConfigurationProperties(prefix = "thread.pool")
@Data
public class CustomThreadPoolConfig {
    private boolean busy;
    private double maxUtilization;
    private int maxWaitingTasksPerThread;
    private int maxExtraThreads;
    private int maxIdleSeconds;
    private String threadName;
    private String rejectedExecutionHandlerClassName;

    @SneakyThrows
    @Bean
    public CustomThreadPool createCustomThreadPool() {
        if(StringUtils.isNotBlank(rejectedExecutionHandlerClassName)) {
            Class c = Class.forName(rejectedExecutionHandlerClassName);
            RejectedExecutionHandler rejectedExecutionHandler =
                    (RejectedExecutionHandler) c.getConstructor().newInstance();
            return new CustomThreadPool(
                    busy,
                    maxUtilization,
                    maxWaitingTasksPerThread,
                    maxExtraThreads,
                    maxIdleSeconds,
                    threadName,
                    rejectedExecutionHandler
            );
        }else {
            return new CustomThreadPool(
                    busy,
                    maxUtilization,
                    maxWaitingTasksPerThread,
                    maxExtraThreads,
                    maxIdleSeconds,
                    threadName,
                    null
            );
        }
    }

}
