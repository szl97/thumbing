package com.thumbing.shared.lock.access;

import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.lock.cache.LockCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 16:58
 */
@Aspect
public class DataBaseAccessAspect {

    @Autowired
    LockCache lockCache;

    private Thread thread;

    private ConcurrentSkipListMap<String, Thread> map;

    public DataBaseAccessAspect(){
        map = new ConcurrentSkipListMap();
        thread = new Thread(
                ()->{
                    while (true){
                        try {
                            Thread.sleep(9000);
                            //todo: 所有正在执行线程的key过期时间重新设置为10s
                            map.entrySet().parallelStream().forEach(
                                    e->{
                                        if(e.getValue().isAlive()&&!e.getValue().isInterrupted()){
                                            lockCache.expire(e.getKey(), 10);
                                        }else {
                                            map.remove(e.getKey());
                                        }
                                    }
                            );
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        thread.setDaemon(true);
        thread.start();
    }

    private void start(String key) {
        map.put(key, Thread.currentThread());
    }

    private void finish(String key) {
        map.remove(key);
    }

    @Around("@annotation(accessLock)")
    public Object AroundExecute(ProceedingJoinPoint joinPoint, AccessLock accessLock) throws Throwable {
        long expire = accessLock.seconds() < 10 ? 10 : accessLock.seconds();
        if(lockCache.lock(accessLock.value(), expire)) {
            start(accessLock.value());
            return joinPoint.proceed();
        }
        else {
            return null;
        }
    }

    @After("@annotation(accessLock)")
    public void afterExecute(AccessLock accessLock){
        finish(accessLock.value());
        lockCache.release(accessLock.value());
    }
}
