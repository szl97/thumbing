package com.thumbing.auth.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtilsForObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 16:47
 */
@Component
public class ValidationCache {
    private final String REGISTER_KEY = CacheKeyConstants.VALIDATION_FOR_REGISTER;
    private final String CHANGE_PASSWORD_KEY = CacheKeyConstants.VALIDATION_FOR_CHANGE_PASSWORD;
    private final long expireTime = 5;
    @Autowired
    private RedisUtilsForObject redisUtilsForObject;

    /**
     * 储存注册时的验证码
     * @param phoneNum
     * @param code
     */
    public void storeRegisterCode(String phoneNum, String code){
        String key = REGISTER_KEY + phoneNum;
        redisUtilsForObject.getRedisUtilsForValue().setWithExpireTime(key, code, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 查找注册时的验证码
     * @param phoneNum
     * @return
     */
    public String findRegisterCode(String phoneNum){
        String key = REGISTER_KEY + phoneNum;
        return redisUtilsForObject.getRedisUtilsForValue().get(key).toString();
    }

    /**
     * 删除注册时的验证码
     * @param phoneNum
     */
    public void removeRegisterCode(String phoneNum){
        String key = REGISTER_KEY + phoneNum;
        redisUtilsForObject.remove(key);
    }

    /**
     * 储存修改密码时的验证码
     * @param userName
     * @param code
     */
    public void storeChangerCode(String userName, String code){
        String key = CHANGE_PASSWORD_KEY + userName;
        redisUtilsForObject.getRedisUtilsForValue().setWithExpireTime(key, code, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 查找修改密码时的验证码
     * @param userName
     * @return
     */
    public String findChangerCode(String userName){
        String key = CHANGE_PASSWORD_KEY + userName;
        return redisUtilsForObject.getRedisUtilsForValue().get(key).toString();
    }

    /**
     * 删除修改密码时的验证码
     * @param userName
     */
    public void removeChangerCode(String userName){
        String key = CHANGE_PASSWORD_KEY + userName;
        if(redisUtilsForObject.hasKey(key)) {
            redisUtilsForObject.remove(key);
        }
    }
}
