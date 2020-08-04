package com.thumbing.auth.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtilsForObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 18:51
 */
@Component
public class FailureLoginCache {
    private final String FAILURE_LOGIN_KEY = CacheKeyConstants.FAILURE_LOGIN;
    @Autowired
    private RedisUtilsForObject redisUtilsForObject;
    private final long expireTime = 120;
    /**
     * 失败次数加一
     * @param userName
     */
    public void increment(String userName){
        String key = FAILURE_LOGIN_KEY + userName;
        Integer times = this.getFailureTimes(userName);
        if(times < 5) {
            redisUtilsForObject.getRedisUtilsForValue().setWithExpireTime(key, this.getFailureTimes(userName) + 1, expireTime, TimeUnit.MINUTES);
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
        return Integer.parseInt(redisUtilsForObject.getRedisUtilsForValue().get(key).toString());
    }

    /**
     * 登录成功后情况失败次数
     * @param userName
     */
    public void clear(String userName){
        if(this.exist(userName)) {
            String key = FAILURE_LOGIN_KEY + userName;
            redisUtilsForObject.remove(key);
        }
    }

    public Boolean exist(String userName){
        String key = FAILURE_LOGIN_KEY + userName;
        return redisUtilsForObject.hasKey(key);
    }
}
