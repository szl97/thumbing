package com.thumbing.shared.thread;

import com.sun.istack.Nullable;
import com.thumbing.shared.utils.cpu.CoresUtils;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.experimental.UtilityClass;

import java.util.concurrent.*;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 17:23
 */

public class CustomThreadPool {

    private ThreadPoolExecutor threadPoolExecutor;

    public CustomThreadPool(Boolean busy,
                            double maxUtilization,
                            int maxWaitingTasksPerThread,
                            int maxExtraThreads,
                            int maxIdleSeconds,
                            String threadName,
                            @Nullable RejectedExecutionHandler rejectedExecutionHandler){

        threadPoolExecutor = newThreadPool(busy,
                maxUtilization,
                maxWaitingTasksPerThread,
                maxExtraThreads,
                maxIdleSeconds,
                threadName,
                rejectedExecutionHandler);
    }

    public void submit(Runnable r){
        threadPoolExecutor.submit(r);
    }

    private ThreadPoolExecutor newThreadPool(Boolean busy, double maxUtilization,
                                            int maxWaitingTasksPerThread,
                                            int maxExtraThreads,
                                            int maxIdleSeconds,
                                            String threadName,
                                            @Nullable RejectedExecutionHandler rejectedExecutionHandler){
        int cores = CoresUtils.getCores();
        int corePoolSize = (int) ((busy ? cores + 1 : cores * 2 + 1) * maxUtilization);
        corePoolSize = corePoolSize == 0 ? 1 : corePoolSize;
        int maxPoolSize = maxExtraThreads + corePoolSize;
        int keepAliveTime = maxIdleSeconds;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> blockingQueue = maxWaitingTasksPerThread == 0?
                new SynchronousQueue():
                maxWaitingTasksPerThread < 0?
                        new LinkedBlockingDeque<>():
                        new ArrayBlockingQueue(maxWaitingTasksPerThread * corePoolSize);
        ThreadFactory factory = new DefaultThreadFactory(threadName);
        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                timeUnit,
                blockingQueue,
                factory,
                rejectedExecutionHandler == null ?
                        new ThreadPoolExecutor.AbortPolicy():
                        rejectedExecutionHandler);
    }
}