package com.thumbing.shared.lock.access;

import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.lock.cache.LockCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 16:58
 */
@Aspect
public class DataBaseAccessAspect {

    @Autowired
    LockCache lockCache;

    @Around("@annotation(accessLock)")
    public Object AroundExecute(ProceedingJoinPoint joinPoint, AccessLock accessLock) throws Throwable {
        if(lockCache.lock(accessLock.value(), accessLock.seconds())) {
            return joinPoint.proceed();
        }
        else {
            return null;
        }
    }

    @After("@annotation(accessLock)")
    public void afterExecute(AccessLock accessLock){
        lockCache.release(accessLock.value());
    }

    @AfterThrowing("@annotation(accessLock)")
    public void afterThrowException(AccessLock accessLock){
        lockCache.release(accessLock.value());
    }
}
