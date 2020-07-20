package com.loserclub.shared.utils.redis;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * @author Stan Sai
 * @date 2020-06-29
 */

@Slf4j
@Data
public class RedisUtilsForObject extends RedisUtils {

    RedisUtilsForValue redisUtilsForValue;
    RedisUtilsForHash redisUtilsForHash;
    private static RedisUtilsForObject INSTANCE = new RedisUtilsForObject();
    private static Object lock = new Object();

    private RedisUtilsForObject() {

    }

    /**
     * 多线程下的单例模式
     * 保证所持有的对象不会被初始化两次
     *
     * @param redisTemplate
     * @return
     */
    public static RedisUtilsForObject getInstance(RedisTemplate redisTemplate) {
        if (INSTANCE.getRedisTemplate() == null) {
            synchronized (lock) {
                if (INSTANCE.getRedisTemplate() == null) {
                    INSTANCE.setRedisTemplate(redisTemplate);
                    INSTANCE.setRedisUtilsForValue(RedisUtilsForValue.getInstance(redisTemplate.opsForValue()));
                    INSTANCE.setRedisUtilsForHash(RedisUtilsForHash.getInstance(redisTemplate.opsForHash()));
                }
            }
        }
        return INSTANCE;
    }
}
