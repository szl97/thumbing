package com.thumbing.shared.lock.uniqueness;

import com.thumbing.shared.annotation.UniqueLock;
import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.lock.cache.LockCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 对uniqueLock注解标注的方法进行动态代理
 * @author Stan Sai
 * @date 2020-08-05 19:38
 */
@Slf4j
@Aspect
@Component
@Conditional(RedisCondition.class)
public class UniqueAspect {
    @Autowired
    private LockCache lockCache;

    private Thread thread;

    private ConcurrentSkipListMap<String, Thread> map;

    public UniqueAspect(){
        map = new ConcurrentSkipListMap();
        thread = new Thread(
                ()->{
                    while (true){
                        try {
                            Thread.sleep(27000);
                            //todo: 所有正在执行线程的key过期时间重新设置为30s
                            map.entrySet().parallelStream().forEach(
                                    e->{
                                        if(e.getValue().isAlive()&&!e.getValue().isInterrupted()){
                                            lockCache.expire(e.getKey(), 30);
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

    private void start(List<String> keys){
        for(String key : keys) {
            map.put(key, Thread.currentThread());
        }
    }

    private void finish(List<String> keys){
        for(String key : keys) {
            map.remove(key);
        }
    }

    @Before("@annotation(uniqueLock)")
    public void beforeExecute(JoinPoint joinPoint, UniqueLock uniqueLock) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> values = getValues(joinPoint, uniqueLock);
        long expire = uniqueLock.seconds() < 30 ? 30 : uniqueLock.seconds();
        for(int i = 0; i < values.size(); i++){
            String v = values.get(i);
            if(!lockCache.lock(v, expire)){
                if(i > 0) {
                    for(int j = i - 1; j >= 0; j--) {
                        lockCache.release(values.get(j));
                    }
                }
                throw new BusinessException("所输入的某个参数已被占用");
            }
        }
        UniqueLockKeyContextHolder.setKeys(values);
        start(values);
    }

    @After("@annotation(uniqueLock)")
    public void afterExecute(UniqueLock uniqueLock) {
        List<String> values = UniqueLockKeyContextHolder.getKeys();
        finish(values);
        lockCache.release(values);
        UniqueLockKeyContextHolder.clear();
    }

    private List<String> getValues(JoinPoint joinPoint, UniqueLock uniqueLock) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] objects = joinPoint.getArgs();
        String className = uniqueLock.className();
        List<String> values = new ArrayList<>();
        for(Object o : objects){
            Class c = o.getClass();
            if(c.getName().equals(className)){
                for(int i = 0; i < uniqueLock.methods().length; i++){
                    Method method = c.getMethod(uniqueLock.methods()[i]);
                    Object fieldObj =  method.invoke(o);
                    if(fieldObj != null){
                        values.add(className + ":" + uniqueLock.methods()[i] + ":" + fieldObj.toString());
                    }
                }
                break;
            }
        }
        values.sort(String::compareTo);
        return values;
    }
}
