package com.thumbing.auth.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 16:29
 */
@Component
public class TokenCache {
    private final String TOKEN_KEY = CacheKeyConstants.TOKEN;
    @Value("${authentication.expirationTime}")
    private long expireTime;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 存储或更新登录后的token
     * @param userName
     * @param token
     */
    public void store(String userName, String token){
        String key = TOKEN_KEY + userName;
        RedisUtilsForValue.setWithExpireTime(redisTemplate.opsForValue(), key, token, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 查找用户的token，若为空则返回null
     * @param userName
     * @return
     */
    public String find(String userName){
        String key = TOKEN_KEY + userName;
        return RedisUtilsForValue.get(redisTemplate.opsForValue(), key);
    }

    /**
     * 删除用户的token
     * @param userName
     */
    public void remove(String userName){
        String key = TOKEN_KEY + userName;
        if(RedisUtils.hasKey(redisTemplate,key)) {
            RedisUtils.remove(redisTemplate,key);
        }
    }
}
