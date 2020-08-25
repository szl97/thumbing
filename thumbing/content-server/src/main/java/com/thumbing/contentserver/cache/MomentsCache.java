package com.thumbing.contentserver.cache;

import cn.hutool.core.util.ArrayUtil;
import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.entity.mongo.content.Moments;
import com.thumbing.shared.utils.redis.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public final static int maxLength = 30000;
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

    /**
     * 获取帖子列表
     * @param from
     * @param end
     * @return
     */
    public List<Moments> getMoments(int from, int end){
        List<String> ids = RedisUtilsForList.get(stringRedisTemplate.opsForList(), momentsList, from, end);
        return ids.parallelStream().map(id->{
            String key = infoMoments+id;
            Moments moments = RedisUtilsForHash.get(momentsRedisTemplate.opsForHash(), key, detailsHashKey);
            if(moments != null){
                Integer thumbingNum = RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), key, thumbingNumHashKey);
                Integer commentsNum = RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), key, commentsNumHashKey);
                Set<Long> userIds = RedisUtilsForSet.members(longRedisTemplate.opsForSet(), thumbingUserIds+id);
                moments.setThumbingNum(thumbingNum);
                moments.setCommentsNum(commentsNum);
                moments.setThumbUserIds(userIds);
            }
            return moments;
        }).collect(Collectors.toList());
    }

    /**
     * 获取帖子列表的长度
     * @return
     */
    public Long sizeOfMomentsList(){
        return RedisUtilsForList.size(stringRedisTemplate.opsForList(), momentsList);
    }

    /**
     * 获取帖子内容
     * @param id
     * @return
     */
    public String getContent(String id){
        return RedisUtilsForHash.get(stringRedisTemplate.opsForHash(), infoMoments+id, contentHashKey);
    }

    /**
     * 获取帖子的下一个匿名昵称
     * @param id
     * @return
     */
    public Integer getNickNameSeq(String id){
        Long seq = RedisUtilsForHash.increment(stringRedisTemplate.opsForHash(), infoMoments+id, nickNameHashKey, 1);
        return seq == null ? null : Integer.parseInt(seq.toString()) - 1;
    }

    /**
     * 获取帖子的评论数和点赞数
     * @param id
     * @return
     */
    public List<Integer> getCommentsNumAndThumbsNum(String id){
        List<String> keys = new ArrayList<>();
        keys.add(commentsChangedSeq);
        keys.add(thumbingNumHashKey);
        return RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), infoMoments+id, keys);
    }

    /**
     * 检查帖子列表是否存在
     * @return
     */
    public Boolean existMomentsList(){
        return RedisUtils.hasKey(stringRedisTemplate, momentsList);
    }


    /**
     * 检查帖子信息是否存在
     * @param id
     * @return
     */
    public Boolean existMomentsInfo(String id){
        return RedisUtils.hasKey(longRedisTemplate, infoMoments+id);
    }

    /**
     * 检查帖子内容是否存在
     * @param id
     * @return
     */
    public Boolean existArticleContent(String id){
        return RedisUtilsForHash.hasKey(stringRedisTemplate.opsForHash(), infoMoments+id, contentHashKey);
    }

    /**
     * 帖子点赞数是否存在
     * @param id
     * @return
     */
    public Boolean existArticleThumbsNum(String id){
        return RedisUtilsForHash.hasKey(integerRedisTemplate.opsForHash(), infoMoments+id, thumbingNumHashKey);
    }

    /**
     * 检查帖子的点赞用户是否存在
     * @param id
     * @return
     */
    public Boolean existThumbingUser(String id){
        return RedisUtils.hasKey(longRedisTemplate, thumbingUserIds+id);
    }

    /**
     * 获取点赞用户集合
     * @param id
     * @return
     */
    public Set<Long> getThumbUserIds(String id){
        return RedisUtilsForSet.members(longRedisTemplate.opsForSet(), thumbingUserIds+id);
    }

    /**
     * 设置key的过期时间
     * @param id
     */
    public void setMomentsExpireTime(String id){
        RedisUtils.expire(stringRedisTemplate, infoMoments+id, expireDay, TimeUnit.DAYS);
        if(existThumbingUser(id)){
            RedisUtils.expire(longRedisTemplate, thumbingUserIds+id, expireDay, TimeUnit.DAYS);
        }
    }

    /**
     * 添加帖子
     * @param moments
     */
    public void addMoments(Moments moments){
        storeMoments(moments);
        Long size = RedisUtilsForList.rightPush(stringRedisTemplate.opsForList(), momentsList, moments.getId());
        if(size > maxLength){
            RedisUtilsForList.clearAndPersist(stringRedisTemplate.opsForList(), momentsList, size-maxLength, size);
        }
    }

    /**
     * 添加帖子 从左到右
     * @param moments
     */
    public void addMomentsWhenInitialize(Moments moments){
        storeMoments(moments);
        Long size = RedisUtilsForList.leftPush(stringRedisTemplate.opsForList(), momentsList, moments.getId());
        if(size > maxLength){
            RedisUtilsForList.clearAndPersist(stringRedisTemplate.opsForList(), momentsList, size-maxLength, size);
        }
    }

    /**
     * 将帖子存入redis
     * @param moments
     */
    public void storeMoments(Moments moments){
        String key = infoMoments+moments.getId();
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put(contentHashKey, moments.getContent());
        RedisUtilsForHash.put(stringRedisTemplate.opsForHash(), key, stringMap);
        Integer thumbs = moments.getThumbingNum();
        Integer comments = moments.getCommentsNum();
        thumbs = thumbs == null ? 0 : thumbs;
        comments = comments == null ? 0 : comments;
        Map<String, Integer> integerMap = new HashMap<>();
        integerMap.put(thumbingNumHashKey, thumbs);
        integerMap.put(commentsNumHashKey, comments);
        integerMap.put(nickNameHashKey, moments.getNickNameSequence());
        RedisUtilsForHash.put(integerRedisTemplate.opsForHash(), key, integerMap);
        if(ArrayUtil.isNotEmpty(moments.getThumbUserIds())){
            Long[] userIds = new Long[moments.getThumbUserIds().size()];
            RedisUtilsForSet.add(longRedisTemplate.opsForSet(), thumbingUserIds+moments.getId(), moments.getThumbUserIds().toArray(userIds));
        }
        RedisUtilsForHash.put(momentsRedisTemplate.opsForHash(), key, detailsHashKey, moments);
        setMomentsExpireTime(moments.getId());
    }

    /**
     * 内容存储
     * @param id
     * @param content
     */
    public void storeContent(String id, String content){
        String abstracts = content.substring(0,100);
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put(contentHashKey, content);
        RedisUtilsForHash.put(stringRedisTemplate.opsForHash(), infoMoments+id, stringMap);
    }

    public void removeArticle(String id){
        RedisUtils.remove(stringRedisTemplate, infoMoments+id);
    }

    /**
     * 点赞或取消点赞
     * @param id
     * @param add
     * @param userId
     */
    public void changeThumbs(String id, boolean add, Long userId){
        if(!existThumbingUser(id) || (RedisUtilsForSet.isExist(longRedisTemplate.opsForSet(), thumbingUserIds+id, userId)^add)) {
            RedisUtilsForHash.increment(integerRedisTemplate.opsForHash(), infoMoments+id, thumbingNumHashKey, add?1:-1);
            RedisUtilsForSet.add(stringRedisTemplate.opsForSet(), thumbingChanged+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), thumbingChangedSeq), id);
            setMomentsExpireTime(id);
        }
    }

    /**
     * 新增评论
     * @param id
     */
    public void addComments(String id){
        RedisUtilsForHash.increment(integerRedisTemplate.opsForHash(), infoMoments+id, commentsNumHashKey, 1);
        RedisUtilsForSet.add(stringRedisTemplate.opsForSet(), commentsChanged+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), commentsChangedSeq), id);
        setMomentsExpireTime(id);
    }

    /**
     * 内容改变
     * @param id
     * @param content
     */
    public void changeContent(String id, String content){
        String abstracts = content.substring(0,100);
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put(contentHashKey, content);
        RedisUtilsForHash.put(stringRedisTemplate.opsForHash(), infoMoments+id, stringMap);
        RedisUtilsForSet.add(stringRedisTemplate.opsForSet(), contentChanged+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), contentChangedSeq), id);
        setMomentsExpireTime(id);
    }

    /**
     * 获取前一阶段所有点赞数或评论数发生改变的帖子
     * @return
     */
    public Set<String> getAndClearThumbsOrCommentsChangedSet() {
        Integer seq = RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), thumbingChangedSeq);
        if (seq < Integer.MAX_VALUE) {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, seq + 1);
        } else {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, 0);
        }
        Integer seq1 = RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), commentsChangedSeq);
        if (seq1 < Integer.MAX_VALUE) {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), commentsChangedSeq, seq1 + 1);
        } else {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), commentsChangedSeq, 0);
        }
        if (RedisUtils.hasKey(stringRedisTemplate, thumbingChanged + seq) && RedisUtils.hasKey(longRedisTemplate, commentsChanged + seq1)) {
            Set<String> set = RedisUtilsForSet.Union(stringRedisTemplate.opsForSet(), thumbingChanged + seq, commentsChanged + seq1);
            return set;
        } else if (RedisUtils.hasKey(stringRedisTemplate, thumbingChanged + seq)) {
            Set<String> set = RedisUtilsForSet.members(stringRedisTemplate.opsForSet(), thumbingChanged + seq);
            RedisUtils.remove(stringRedisTemplate, thumbingChanged + seq);
            return set;
        } else if (RedisUtils.hasKey(stringRedisTemplate, commentsChanged + seq1)) {
            Set<String> set = RedisUtilsForSet.members(stringRedisTemplate.opsForSet(), commentsChanged + seq1);
            RedisUtils.remove(stringRedisTemplate, commentsChanged + seq1);
            return set;
        } else {
            return null;
        }
    }


    /**
     * 获取评论数
     * @param id
     * @return
     */
    public int getMomentsCommentsNum(String id){
        Integer i = RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), infoMoments+id, commentsNumHashKey);
        return i == null ? 0 : i;
    }

    /**
     * 获取当前的匿名昵称序号
     */
    public int getCurrentNickNameSeq(String id){
        return RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), infoMoments+id, nickNameHashKey);
    }

    /**
     * 获取前一阶段所有内容发生改变的帖子
     * @return
     */
    public Set<String> getAndClearContentChangedSet() {
        Integer seq = RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), contentChangedSeq);
        if (seq < Integer.MAX_VALUE) {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), contentChangedSeq, seq + 1);
        } else {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), contentChangedSeq, 0);
        }
        if (RedisUtils.hasKey(stringRedisTemplate, contentChanged + seq)) {
            Set<String> set = RedisUtilsForSet.members(stringRedisTemplate.opsForSet(), contentChanged + seq);
            RedisUtils.remove(stringRedisTemplate, contentChanged + seq);
            return set;
        } else {
            return null;
        }
    }
}
