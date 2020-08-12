package com.thumbing.shared.lock.access;

import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.lock.cache.LockCache;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
        StringBuilder key = new StringBuilder(accessLock.value());
        if(accessLock.fields().length > 0 && StringUtils.isNotBlank(accessLock.className())){
            String className = accessLock.className();
            Object[] objects = joinPoint.getArgs();
            for(Object o : objects){
                Class c = o.getClass();
                if(c.getName().equals(className)){
                    for(int i = 0; i < accessLock.fields().length; i++){
                        Method method = c.getMethod(accessLock.fields()[i]);
                        Object fieldObj =  method.invoke(o);
                        if(fieldObj != null){
                            key.append(":"+fieldObj.toString());
                        }
                    }
                    break;
                }
            }
        }
        if(lockCache.lock(key.toString(), expire)) {
            start(key.toString());
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
