package com.thumbing.auth.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 储存注册时的验证码
     * @param phoneNum
     * @param code
     */
    public void storeRegisterCode(String phoneNum, String code){
        String key = REGISTER_KEY + phoneNum;
        RedisUtilsForValue.setWithExpireTime(redisTemplate.opsForValue(), key, code, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 查找注册时的验证码
     * @param phoneNum
     * @return
     */
    public String findRegisterCode(String phoneNum){
        String key = REGISTER_KEY + phoneNum;
        return RedisUtilsForValue.get(redisTemplate.opsForValue(), key);
    }

    /**
     * 删除注册时的验证码
     * @param phoneNum
     */
    public void removeRegisterCode(String phoneNum){
        String key = REGISTER_KEY + phoneNum;
        RedisUtils.remove(redisTemplate, key);
    }

    /**
     * 储存修改密码时的验证码
     * @param userName
     * @param code
     */
    public void storeChangerCode(String userName, String code){
        String key = CHANGE_PASSWORD_KEY + userName;
        RedisUtilsForValue.setWithExpireTime(redisTemplate.opsForValue(), key, code, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 查找修改密码时的验证码
     * @param userName
     * @return
     */
    public String findChangerCode(String userName){
        String key = CHANGE_PASSWORD_KEY + userName;
        return RedisUtilsForValue.get(redisTemplate.opsForValue(), key);
    }

    /**
     * 删除修改密码时的验证码
     * @param userName
     */
    public void removeChangerCode(String userName){
        String key = CHANGE_PASSWORD_KEY + userName;
        if(RedisUtils.hasKey(redisTemplate, key)) {
            RedisUtils.remove(redisTemplate, key);
        }
    }
}
