package com.thumbing.usermanagement.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtilsForValue;
import com.thumbing.usermanagement.dto.output.PersonalConfigurationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 10:11
 */
@Component
public class PersonalConfigurationCache {
    private final String KEY = CacheKeyConstants.PERSONAL_CONFIGURATION;
    @Autowired
    private RedisTemplate<String, PersonalConfigurationDto> redisTemplate;

    public PersonalConfigurationDto get(){
        return RedisUtilsForValue.get(redisTemplate.opsForValue(), KEY);
    }

    public void set(PersonalConfigurationDto dto){
        RedisUtilsForValue.set(redisTemplate.opsForValue(), KEY, dto);
    }
}
