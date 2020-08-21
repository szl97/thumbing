package com.thumbing.contentserver.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.entity.mongo.content.Moments;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/21 17:12
 */
@Component
public class MomentsCache {
    @Autowired
    private RedisTemplate<String, Moments> momentsRedisTemplate;
    @Autowired
    private RedisTemplate<String, Integer> integerRedisTemplate;
    @Autowired
    private RedisTemplate<String, Long> longRedisTemplate;
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String,String> stringRedisTemplate;
    private final short expireDay = 30;
    private final int maxLength = 30000;
    //key
    //list
    private final String momentsList = CacheKeyConstants.MOMENTS_LIST;
    //hash
    private final String infoMoments = CacheKeyConstants.INFO_MOMENTS;
    private final String nickNameHashKey = CacheKeyConstants.NICK_NAME_SEQUENCE;
    private final String thumbingNumHashKey = CacheKeyConstants.THUMBING_NUM;
    private final String commentsNumHashKey = CacheKeyConstants.COMMENTS_NUM;
    private final String detailsHashKey = CacheKeyConstants.DETAILS;
    private final String contentHashKey = CacheKeyConstants.CONTENT;
    //set
    private final String thumbingUserIds = CacheKeyConstants.MOMENTS_THUMBING_USER_SET;
    //changed
    private final String thumbingChanged = CacheKeyConstants.MOMENTS_CHANGED_THUMBING_NUM;
    private final String commentsChanged = CacheKeyConstants.MOMENTS_CHANGED_COMMENTS_NUM;
    private final String contentChanged = CacheKeyConstants.MOMENTS_CHANGED_CONTENT;
    //string
    //seq
    private final String thumbingChangedSeq = CacheKeyConstants.MOMENTS_CHANGED_THUMBING_SEQ;
    private final String commentsChangedSeq = CacheKeyConstants.MOMENTS_CHANGED_COMMENTS_SEQ;
    private final String contentChangedSeq = CacheKeyConstants.MOMENTS_CHANGED_CONTENT_SEQ;

    @PostConstruct
    public void postConstruct(){
        if(!RedisUtils.hasKey(integerRedisTemplate, thumbingChangedSeq)){
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, 0);
        }
        if(!RedisUtils.hasKey(integerRedisTemplate, commentsChangedSeq)){
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), commentsChangedSeq, 0);
        }
        if(!RedisUtils.hasKey(integerRedisTemplate, contentChangedSeq)){
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), contentChangedSeq, 0);
        }
    }


}
