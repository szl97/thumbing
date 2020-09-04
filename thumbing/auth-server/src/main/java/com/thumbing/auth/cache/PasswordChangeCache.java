package com.thumbing.auth.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author Stan Sai
 * @date 2020-09-04 20:46
 */
@Component
public class PasswordChangeCache {
    private final String key = CacheKeyConstants.CHANGE_PASSWORD;
    @Autowired
    private RedisTemplate<String, LocalDateTime> redisTemplate;
    @Value("${authentication.expirationTime}")
    private short expireMinutes;

    public boolean exist(Long userId){
        return RedisUtils.hasKey(redisTemplate, key+userId);
    }

    public LocalDateTime getChangeTime(Long userId){
        return RedisUtilsForValue.get(redisTemplate.opsForValue(), key+userId);
    }

    public void setChangeTime(Long userId, LocalDateTime time){
       RedisUtilsForValue.setWithExpireTime(redisTemplate.opsForValue(), key+userId, time, expireMinutes, TimeUnit.MINUTES);
    }
}
