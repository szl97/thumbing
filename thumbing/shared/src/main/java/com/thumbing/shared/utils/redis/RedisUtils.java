package com.thumbing.shared.utils.redis;

import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author Stan Sai
 * @date 2020-06-29
 */
@Slf4j
@Data
@UtilityClass
public class RedisUtils {


    /**
     * 删除某个key
     * @param key
     * @return
     */
    public Boolean remove(RedisTemplate redisTemplate, String key) {

        return redisTemplate.delete(key);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean hasKey(RedisTemplate redisTemplate, String key) {

        return redisTemplate.hasKey(key);
    }

    /**
     * 设置key的过期时间
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public Boolean expire(RedisTemplate redisTemplate, String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }
}
