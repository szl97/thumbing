package com.thumbing.shared.uniqueness;

import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Stan Sai
 * @date 2020-08-05 21:12
 */
@Component
public class UniqueLockCache {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Boolean lock(String key, long seconds){
        return RedisUtilsForValue.setNx(redisTemplate.opsForValue(), key, "lock", seconds, TimeUnit.SECONDS);
    }

    public void release(List<String> keys){
       RedisUtils.removeAll(redisTemplate, keys);
    }
}
