package com.thumbing.recordserver.cache;

import com.thumbing.recordserver.dto.output.ChatRecordDto;
import com.thumbing.shared.constants.CacheKeyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 17:00
 */
@Component
public class ChatRecordCache {
    private final static String KEY = CacheKeyConstants.CHAT_RECORD;
    @Autowired
    private RedisTemplate<String, ChatRecordDto> redisTemplate;
}
