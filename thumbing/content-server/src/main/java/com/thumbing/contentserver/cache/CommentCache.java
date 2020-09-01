package com.thumbing.contentserver.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.entity.mongo.content.Comment;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.utils.redis.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private final String commentsNewSet = CacheKeyConstants.COMMENTS_NEW;
    private final String commentsDeletedSet = CacheKeyConstants.COMMENTS_DELETED;
    private final String thumbingChangedSet = CacheKeyConstants.COMMENTS_THUMBING_CHANGED;
    //string
    //seq
    private final String commentsNewSeq = CacheKeyConstants.COMMENTS_NEW_SEQ;
    private final String commentsDeletedSeq = CacheKeyConstants.COMMENTS_DELETED_SEQ;
    private final String thumbingChangedSeq = CacheKeyConstants.COMMENTS_CHANGED_THUMBING_SEQ;
    //hash
    private final String commentsDetails = CacheKeyConstants.COMMENTS_DETAILS;
    private final String thumbingNumHashKey = CacheKeyConstants.THUMBING_NUM;
    private final String detailsHashKey = CacheKeyConstants.DETAILS;

    @PostConstruct
    public void postConstruct(){
        if(!RedisUtils.hasKey(integerRedisTemplate, commentsNewSeq)){
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), commentsNewSeq, 0);
        }
        if(!RedisUtils.hasKey(integerRedisTemplate, thumbingChangedSeq)){
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, 0);
        }
        if(!RedisUtils.hasKey(integerRedisTemplate, commentsDeletedSeq)){
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), commentsDeletedSeq, 0);
        }
    }

    /**
     * 根据文章或动态Id获取第一级评论列表
     * @param id
     * @return
     */
    public List<Comment> getParentsCommentsList(String id, ContentType type, int from, int end){
        String key = (type == ContentType.ARTICLE ? articleCommentsIdList : momentsCommentsIdList) + id;
        List<Long> parentCommentsIds = RedisUtilsForList.get(longRedisTemplate.opsForList(), key, from, end);
        return parentCommentsIds.parallelStream().map(i->getCommentDetailsAndSetExpireTime(i)).collect(Collectors.toList());
    }

    /**
     * 根据父评论的commentsId获取子评论
     * @param commentsId
     * @return
     */
    public List<Comment> getChildCommentsList(Long commentsId,  int from, int end){
        String key = childCommentsIdList+commentsId;
        List<Long> childCommentsIds = RedisUtilsForList.get(longRedisTemplate.opsForList(), key, from, end);
        return childCommentsIds.parallelStream().map(i->getCommentDetailsAndSetExpireTime(i)).collect(Collectors.toList());
    }

    /**
     * 获取评论详情并且设置过期时间
     * @param id
     * @return
     */
    public Comment getCommentDetailsAndSetExpireTime(Long id){
        Comment comment = getCommentDetails(id);
        setExpireTime(id);
        return comment;
    }

    /**
     * 根据commentsId获取评论详情
     * @param id
     * @return
     */
    public Comment getCommentDetails(Long id){
        Comment comment = RedisUtilsForHash.get(commentRedisTemplate.opsForHash(), commentsDetails+id, detailsHashKey);
        if(comment == null) return null;
        Integer thumbs = RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), commentsDetails+id, thumbingNumHashKey);
        thumbs = thumbs == null ? 0 : thumbs;
        comment.setThumbingNum(thumbs);
        Set<Long> thumbUserIds = RedisUtilsForSet.members(longRedisTemplate.opsForSet(), thumbingUserIds+id);
        if(CollectionUtils.isNotEmpty(thumbUserIds)) {
            comment.setThumbUserIds(thumbUserIds);
        }
        return comment;
    }

    public Comment getCommentNoChangedInfo(Long id){
        return RedisUtilsForHash.get(commentRedisTemplate.opsForHash(), commentsDetails+id, detailsHashKey);
    }

    /**
     * 设置comments的过期时间
     * @param id
     */
    public void setExpireTime(Long id){
        if(RedisUtils.hasKey(longRedisTemplate, childCommentsIdList+id)) {
            RedisUtils.expire(longRedisTemplate, childCommentsIdList+id, expireDay, TimeUnit.DAYS);
        }
        if(RedisUtils.hasKey(longRedisTemplate, thumbingUserIds+id)){
            RedisUtils.expire(longRedisTemplate, thumbingUserIds+id, expireDay, TimeUnit.DAYS);
        }
        RedisUtils.expire(stringRedisTemplate, commentsDetails+id, expireDay, TimeUnit.DAYS);
    }

    /**
     * 设置评论列表的过期时间
     * @param key
     */
    public void setExpireTimeForCommentsList(String key){
        RedisUtils.expire(longRedisTemplate, key, expireDay, TimeUnit.DAYS);
    }

    /**
     * 添加新评论
     * @param comment
     */
    public void addComments(Comment comment){
        storeComments(comment);
        RedisUtilsForSet.add(longRedisTemplate.opsForSet(), commentsNewSet+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), commentsNewSeq), comment.getCommentId());
    }

    /**
     * 存储评论到redis中
     * @param comment
     */
    public void storeComments(Comment comment){
        storeCommentsDetails(comment);
        String key = comment.getParentCommentId() == null
                ? (comment.getContentType() == ContentType.ARTICLE
                ? articleCommentsIdList+comment.getContentId()
                : momentsCommentsIdList+comment.getContentId())
                : childCommentsIdList+comment.getParentCommentId();
        RedisUtilsForList.rightPush(longRedisTemplate.opsForList(), key, comment.getCommentId());
        setExpireTimeForCommentsList(key);
    }

    /**
     * 将comment的详情存储到redis中
     * @param comment
     */
    public void storeCommentsDetails(Comment comment){
        String key = commentsDetails + comment.getCommentId();
        Integer thumbsNum = comment.getThumbingNum();
        thumbsNum = thumbsNum == null ? 0 : thumbsNum;
        RedisUtilsForHash.put(integerRedisTemplate.opsForHash(), key, thumbingNumHashKey, thumbsNum);
        Set<Long> thumbsUserIds = comment.getThumbUserIds();
        if(CollectionUtils.isNotEmpty(thumbsUserIds)){
            Long[] array = new Long[thumbsUserIds.size()];
            RedisUtilsForSet.add(longRedisTemplate.opsForSet(), thumbingUserIds+comment.getCommentId(), thumbsUserIds.toArray(array));
        }
        RedisUtilsForHash.put(commentRedisTemplate.opsForHash(), key, detailsHashKey, comment);
        setExpireTime(comment.getCommentId());
    }

    /**
     * 删除评论
     * @param commentsId
     */
    public void deleteComments(Long commentsId){
        String key = commentsDetails+commentsId;
        Comment comment = RedisUtilsForHash.get(commentRedisTemplate.opsForHash(), key, detailsHashKey);
        if(comment != null) {
            comment.setIsDelete(1);
            RedisUtilsForHash.put(commentRedisTemplate.opsForHash(), key, detailsHashKey, comment);
            RedisUtilsForSet.add(longRedisTemplate.opsForSet(), commentsDeletedSet+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), commentsDeletedSeq), commentsId);
        }
    }

    /**
     * 点赞数变化
     * @param commentsId
     * @param add
     */
    public void thumbsChanged(Long commentsId, Long userId, boolean add){
        if(!existThumbingUser(commentsId) || (RedisUtilsForSet.isExist(longRedisTemplate.opsForSet(), thumbingUserIds+commentsId, userId)^add)) {
            Long l = add ?  RedisUtilsForSet.add(longRedisTemplate.opsForSet(), thumbingUserIds+commentsId, userId) : RedisUtilsForSet.remove(longRedisTemplate.opsForSet(), thumbingUserIds+commentsId, userId);
            RedisUtilsForHash.increment(integerRedisTemplate.opsForHash(), commentsDetails+commentsId, thumbingNumHashKey, add?1:-1);
            if(!RedisUtilsForSet.isExist(longRedisTemplate.opsForSet(), commentsNewSet+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), commentsNewSeq), commentsId)) {
                RedisUtilsForSet.add(longRedisTemplate.opsForSet(), thumbingChangedSet + RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), thumbingChangedSeq), commentsId);
            }
        }
    }

    /**
     * 检查评论的点赞用户是否存在
     * @param id
     * @return
     */
    public Boolean existThumbingUser(Long id){
        return RedisUtils.hasKey(longRedisTemplate, thumbingUserIds+id);
    }

    /**
     * 检查评论的点赞用户是否存在
     * @param id
     * @return
     */
    public Boolean existCommentThumbsNum(Long id){
        return RedisUtilsForHash.hasKey(integerRedisTemplate.opsForHash(), commentsDetails+id, thumbingNumHashKey);
    }

    /**
     * 获取点赞用户集合
     * @param id
     * @return
     */
    public Set<Long> getThumbUserIds(Long id){
        return RedisUtilsForSet.members(longRedisTemplate.opsForSet(), thumbingUserIds+id);
    }

    public Boolean existArticleComments(String id){
        return RedisUtils.hasKey(longRedisTemplate, articleCommentsIdList+id);
    }

    public Boolean existMomentsComments(String id){
        return RedisUtils.hasKey(longRedisTemplate, momentsCommentsIdList+id);
    }

    public Boolean existChildComments(Long id){
        return RedisUtils.hasKey(longRedisTemplate, childCommentsIdList+id);
    }

    /**
     * 获取一段时间内新增的Comment
     * @return
     */
    public List<Comment> getAndClearNewComments(){
        Integer seq = RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), commentsNewSeq);
        if (seq < Integer.MAX_VALUE) {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), commentsNewSeq, seq + 1);
        } else {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), commentsNewSeq, 0);
        }
        if (RedisUtils.hasKey(longRedisTemplate, commentsNewSet + seq)) {
            Set<Long> set = RedisUtilsForSet.members(longRedisTemplate.opsForSet(), commentsNewSet + seq);
            RedisUtils.remove(longRedisTemplate, commentsNewSet + seq);
            return set.parallelStream().map(id->getCommentDetails(id)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 获取一段时间内点赞数发生变化的comment
     * @return
     */
    public Set<Long> getAndClearThumbsNumChanged(){
        Integer seq = RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), thumbingChangedSeq);
        if (seq < Integer.MAX_VALUE) {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, seq + 1);
        } else {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, 0);
        }
        if (RedisUtils.hasKey(longRedisTemplate, thumbingChangedSet + seq)) {
            Set<Long> set = RedisUtilsForSet.members(longRedisTemplate.opsForSet(), thumbingChangedSet + seq);
            RedisUtils.remove(longRedisTemplate, thumbingChangedSet + seq);
            return set;
        } else {
            return null;
        }
    }

    /**
     * 获取一段时间内删除的commentsId
     * @return
     */
    public Set<Long> getAndClearDeletedCommentsId(){
        Integer seq = RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), thumbingChangedSeq);
        if (seq < Integer.MAX_VALUE) {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, seq + 1);
        } else {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, 0);
        }
        if (RedisUtils.hasKey(longRedisTemplate, thumbingChangedSet + seq)) {
            Set<Long> set = RedisUtilsForSet.members(longRedisTemplate.opsForSet(), thumbingChangedSet + seq);
            RedisUtils.remove(longRedisTemplate, thumbingChangedSet + seq);
            return set;
        } else {
            return null;
        }
    }

    /**
     * 获取评论的点赞数
     * @param commentsId
     * @return
     */
    public Integer getThumbsNum(Long commentsId){
        Integer thumbs = RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), commentsDetails+commentsId, thumbingNumHashKey);
        return thumbs == null ? 0 : thumbs;
    }
}
