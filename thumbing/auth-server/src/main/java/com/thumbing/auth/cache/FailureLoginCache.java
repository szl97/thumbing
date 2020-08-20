package com.thumbing.auth.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 18:51
 */
@Component
public class FailureLoginCache {
    private final String FAILURE_LOGIN_KEY = CacheKeyConstants.FAILURE_LOGIN;
    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;
    private final short expireTime = 120;
    /**
     * 失败次数加一
     * @param userName
     */
    public void increment(String userName){
        String key = FAILURE_LOGIN_KEY + userName;
        if(!this.exist(userName)){
            RedisUtilsForValue.setWithExpireTime(redisTemplate.opsForValue(), key, 1, expireTime, TimeUnit.MINUTES);
        }
        else {
            RedisUtilsForValue.increment(redisTemplate.opsForValue(), key, 1l);
        }
    }

    /**
     * 查询失败次数
     * @param userName
     * @return
     */
    public Integer getFailureTimes(String userName){
        if(!this.exist(userName)){
            return 0;
        }
        String key = FAILURE_LOGIN_KEY + userName;
        return RedisUtilsForValue.get(redisTemplate.opsForValue(), key);
    }

    /**
     * 登录成功后情况失败次数
     * @param userName
     */
    public void clear(String userName){
        if(this.exist(userName)) {
            String key = FAILURE_LOGIN_KEY + userName;
            RedisUtils.remove(redisTemplate, key);
        }
    }

    public Boolean exist(String userName){
        String key = FAILURE_LOGIN_KEY + userName;
        return RedisUtils.hasKey(redisTemplate, key);
    }
}
