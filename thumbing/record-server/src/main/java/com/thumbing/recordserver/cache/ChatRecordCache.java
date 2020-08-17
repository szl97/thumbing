package com.thumbing.recordserver.cache;

import com.thumbing.recordserver.dto.output.ChatRecordDto;
import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 17:00
 */
@Component
public class ChatRecordCache {
    private final static String KEY = CacheKeyConstants.CHAT_RECORD;
    @Autowired
    private RedisTemplate<String, ChatRecordDto> redisTemplate;
    private final long expireMinutes = 500;

    public void setRecord(ChatRecordDto dto){
        RedisUtilsForList.rightPush(redisTemplate.opsForList(), KEY+dto.getUserId1()+":"+dto.getUserId2(), dto);
        RedisUtils.expire(redisTemplate, KEY+dto.getUserId1()+":"+dto.getUserId2(), expireMinutes, TimeUnit.MINUTES);
        Long len = size(dto.getUserId1(), dto.getUserId2());
        if(len > 100) {
            RedisUtilsForList.clearAndPersist(redisTemplate.opsForList(), KEY+dto.getUserId1()+":"+dto.getUserId2(), len - 100, len);
        }
    }

    public List<ChatRecordDto> getRecord(Long userId1, Long userId2, int start, int end){
        return RedisUtilsForList.get(redisTemplate.opsForList(), KEY+userId1+":"+userId2, start, end);
    }

    public Boolean exit(Long userId1, Long userId2){
        return RedisUtils.hasKey(redisTemplate, KEY+userId1+":"+userId2);
    }

    public Long size(Long userId1, Long userId2){
        return RedisUtilsForList.size(redisTemplate.opsForList(), KEY+userId1+":"+userId2);
    }

    public void remove(Long userId1, Long userId2){
        RedisUtils.remove(redisTemplate, KEY+userId1+":"+userId2);
    }

    public void removeOne(Long userId1, Long userId2, ChatRecordDto dto){
        RedisUtilsForList.remove(redisTemplate.opsForList(), KEY+userId1+":"+userId2, 1, dto);
    }
}
