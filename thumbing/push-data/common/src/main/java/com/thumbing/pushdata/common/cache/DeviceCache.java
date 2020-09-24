package com.thumbing.pushdata.common.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/9/24 14:46
 */
@Component
public class DeviceCache {
    private final static String KEY = CacheKeyConstants.DEVICE_CONNECTION_SET;
    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    public void add(String nodeName, Long deviceId){
        RedisUtilsForSet.add(redisTemplate.opsForSet(), KEY+nodeName, deviceId);
    }

    public void remove(String nodeName, Long deviceId){
        RedisUtilsForSet.remove(redisTemplate.opsForSet(), KEY+nodeName, deviceId);
    }

    public void  remove(String nodeName){
        RedisUtils.remove(redisTemplate, KEY+nodeName);
    }

    public boolean contains(String nodeName, Long deviceId){
        return RedisUtilsForSet.isExist(redisTemplate.opsForSet(), KEY+nodeName, deviceId);
    }

    public long count(String nodeName){
        return RedisUtilsForSet.size(redisTemplate.opsForSet(), KEY+nodeName);
    }
}
