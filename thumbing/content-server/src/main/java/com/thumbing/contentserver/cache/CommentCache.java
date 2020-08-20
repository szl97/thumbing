package com.thumbing.contentserver.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.entity.mongo.content.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/20 9:00
 */
@Component
public class CommentCache {
    @Autowired
    private RedisTemplate<String, Comment> commentRedisTemplate;
    @Autowired
    private RedisTemplate<String, Long> longRedisTemplate;
    @Autowired
    private RedisTemplate<String, Integer> integerRedisTemplate;
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String,String> stringRedisTemplate;
    private final short expireDay = 30;
    //key
    //list
    private final String articleCommentsIdList = CacheKeyConstants.COMMENTS_ARTICLES;
    private final String momentsCommentsIdList = CacheKeyConstants.COMMENTS_MOMENTS;
    private final String childCommentsIdList = CacheKeyConstants.CHILD_COMMENTS;
    //set
    private final String thumbingUserIds = CacheKeyConstants.COMMENTS_THUMBING_USER_SET;
    //changed
    private final String commentsDeletedSet = CacheKeyConstants.COMMENTS_DELETED;
    private final String thumbingChangedSet = CacheKeyConstants.COMMENTS_THUMBING_CHANGED;
    //string
    //seq
    private final String commentsDeletedSeq = CacheKeyConstants.COMMENTS_DELETED_SEQ;
    private final String thumbingChangedSeq = CacheKeyConstants.COMMENTS_CHANGED_THUMBING_SEQ;
    //hash
    private final String commentsDetails = CacheKeyConstants.COMMENTS_DETAILS;
    private final String thumbingNumHashKey = CacheKeyConstants.THUMBING_NUM;
    private final String detailsHashKey = CacheKeyConstants.DETAILS;
}
