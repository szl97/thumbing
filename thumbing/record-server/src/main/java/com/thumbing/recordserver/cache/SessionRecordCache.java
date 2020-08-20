package com.thumbing.recordserver.cache;

import com.thumbing.recordserver.dto.output.SessionRecordDto;
import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Stan Sai
 * @date 2020-08-16 18:00
 */
@Component
public class SessionRecordCache {
    private final static String KEY = CacheKeyConstants.SESSION_RECORD;
    @Autowired
    private RedisTemplate<String, SessionRecordDto> redisTemplate;

    private final short expireDays = 30;

    public void set(Long userId, SessionRecordDto dto){
        RedisUtilsForHash.put(redisTemplate.opsForHash(),KEY+userId, dto.getTargetUserId().toString(), dto);
        RedisUtils.expire(redisTemplate, KEY+userId, expireDays, TimeUnit.DAYS);
    }

    public SessionRecordDto getOne(Long userId, Long targetId){
        return RedisUtilsForHash.get(redisTemplate.opsForHash(),KEY+userId, targetId.toString());
    }

    public List<SessionRecordDto> getAll(Long userId){
        return RedisUtilsForHash.getAllValues(redisTemplate.opsForHash(),KEY+userId);
    }

    public Boolean exit(Long userId, Long targetId){
        return RedisUtilsForHash.hasKey(redisTemplate.opsForHash(), KEY+userId, targetId.toString());
    }

    public void removeAll(Long userId){
        RedisUtils.remove(redisTemplate, KEY+userId);
    }

    public void remove(Long userId, Long targetId){
        RedisUtilsForHash.remove(redisTemplate.opsForHash(), KEY+userId, targetId.toString());
    }
}
