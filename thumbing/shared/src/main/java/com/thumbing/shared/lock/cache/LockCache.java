package com.thumbing.shared.lock.cache;

import com.thumbing.shared.condition.RabbitCondition;
import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Stan Sai
 * @date 2020-08-05 21:12
 */
@Conditional(RedisCondition.class)
@Component
public class LockCache {
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    public Boolean lock(String key, long seconds){
        return RedisUtilsForValue.setNx(redisTemplate.opsForValue(), key, "lock", seconds, TimeUnit.SECONDS);
    }

    public void release(List<String> keys){
       RedisUtils.removeAll(redisTemplate, keys);
    }

    public void release(String key){
        RedisUtils.remove(redisTemplate, key);
    }

    public void expire(String key, long seconds){
        RedisUtils.expire(redisTemplate, key, seconds, TimeUnit.SECONDS);
    }
}
