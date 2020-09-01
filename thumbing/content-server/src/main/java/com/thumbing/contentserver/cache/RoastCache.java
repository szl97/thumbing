package com.thumbing.contentserver.cache;

import cn.hutool.core.util.ArrayUtil;
import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.entity.mongo.content.Roast;
import com.thumbing.shared.utils.redis.*;
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
 * @Date: 2020/8/27 15:10
 */
@Component
public class RoastCache {
    @Autowired
    private RedisTemplate<String, Roast> roastRedisTemplate;
    @Autowired
    private RedisTemplate<String, Integer> integerRedisTemplate;
    @Autowired
    private RedisTemplate<String, Long> longRedisTemplate;
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String,String> stringRedisTemplate;
    private final short expireDay = 30;
    public final static int maxLength = 50000;
    //key
    //set
    private final String roastSet = CacheKeyConstants.ROAST_SET;
    //list
    private final String roastList = CacheKeyConstants.ROAST_LIST;
    //hash
    private final String infoRoast = CacheKeyConstants.INFO_ROAST;
    private final String thumbingNumHashKey = CacheKeyConstants.THUMBING_NUM;
    private final String detailsHashKey = CacheKeyConstants.DETAILS;
    //set
    private final String thumbingUserIds = CacheKeyConstants.ROAST_THUMBING_USER_SET;
    //changed
    private final String thumbingChanged = CacheKeyConstants.ROAST_CHANGED_THUMBING_NUM;
    //string
    //seq
    private final String thumbingChangedSeq = CacheKeyConstants.ROAST_CHANGED_THUMBING_SEQ;

    @PostConstruct
    public void postConstruct(){
        if(!RedisUtils.hasKey(integerRedisTemplate, thumbingChangedSeq)){
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, 0);
        }
    }

    /**
     * 获取吐槽列表
     * @return
     */
    public List<Roast> getRoasts(){
        Set<String> ids = RedisUtilsForSet.distinctRandomMembers(stringRedisTemplate.opsForSet(), roastSet, 100);
        return ids.parallelStream().map(id-> getArticle(id)).collect(Collectors.toList());
    }

    public Roast getArticle(String id){
        String key = infoRoast+id;
        Roast article = RedisUtilsForHash.get(roastRedisTemplate.opsForHash(), key, detailsHashKey);
        if(article != null){
            Integer thumbingNum = RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), key, thumbingNumHashKey);
            Set<Long> userIds = RedisUtilsForSet.members(longRedisTemplate.opsForSet(), thumbingUserIds+id);
            article.setThumbingNum(thumbingNum);
            article.setThumbUserIds(userIds);
        }
        return article;
    }

    /**
     * 获取吐槽列表的长度
     * @return
     */
    public Long sizeOfArticleList(){
        return RedisUtilsForList.size(stringRedisTemplate.opsForList(), roastList);
    }

    /**
     * 获取吐槽的固定信息
     * @param id
     * @return
     */
    public Roast getRoastNoChangedInfo(String id){
        return RedisUtilsForHash.get(roastRedisTemplate.opsForHash(), infoRoast+id, detailsHashKey);
    }


    /**
     * 获取吐槽的点赞数
     * @param id
     * @return
     */
    public Integer getThumbsNum(String id){
        return RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), infoRoast+id, thumbingNumHashKey);
    }

    /**
     * 检查吐槽列表是否存在
     * @return
     */
    public Boolean existRoastSet(){
        return RedisUtils.hasKey(stringRedisTemplate, roastSet);
    }

    /**
     * 检查吐槽信息是否存在
     * @param id
     * @return
     */
    public Boolean existRoastInfo(String id){
        return RedisUtils.hasKey(longRedisTemplate, infoRoast+id);
    }

    /**
     * 吐槽点赞数是否存在
     * @param id
     * @return
     */
    public Boolean existRoastThumbsNum(String id){
        return RedisUtilsForHash.hasKey(integerRedisTemplate.opsForHash(), infoRoast+id, thumbingNumHashKey);
    }

    /**
     * 检查吐槽的点赞用户是否存在
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
    public void setRoastExpireTime(String id){
        RedisUtils.expire(stringRedisTemplate, infoRoast+id, expireDay, TimeUnit.DAYS);
        if(existThumbingUser(id)){
            RedisUtils.expire(longRedisTemplate, thumbingUserIds+id, expireDay, TimeUnit.DAYS);
        }
    }

    /**
     * 添加吐槽
     * @param roast
     */
    public void addRoast(Roast roast){
        storeRoast(roast);
        RedisUtilsForSet.add(stringRedisTemplate.opsForSet(), roastSet, roast.getId());
        Long size = RedisUtilsForList.leftPush(stringRedisTemplate.opsForList(), roastList, roast.getId());
        if(size > maxLength){
            List<String> removes = RedisUtilsForList.get(stringRedisTemplate.opsForList(), roastList, maxLength+1 , size);
            RedisUtilsForList.clearAndPersist(stringRedisTemplate.opsForList(), roastList, 0, maxLength);
            String[] a = new String[removes.size()];
            RedisUtilsForSet.remove(stringRedisTemplate.opsForSet(), roastSet, removes.toArray(a));
        }
    }

    /**
     * 添加吐槽
     * @param roast
     */
    public void addRoastWhenInitialize(Roast roast){
        storeRoast(roast);
        RedisUtilsForSet.add(stringRedisTemplate.opsForSet(), roastSet, roast.getId());
        Long size = RedisUtilsForList.rightPush(stringRedisTemplate.opsForList(), roastList, roast.getId());
        if(size > maxLength){
            List<String> removes = RedisUtilsForList.get(stringRedisTemplate.opsForList(), roastList, maxLength+1 , size);
            RedisUtilsForList.clearAndPersist(stringRedisTemplate.opsForList(), roastList, 0, maxLength);
            String[] a = new String[removes.size()];
            RedisUtilsForSet.remove(stringRedisTemplate.opsForSet(), roastSet, removes.toArray(a));
        }
    }

    /**
     * 将吐槽存入redis
     * @param roast
     */
    public void storeRoast(Roast roast){
        String key = infoRoast+roast.getId();
        Integer thumbs = roast.getThumbingNum();
        thumbs = thumbs == null ? 0 : thumbs;
        RedisUtilsForHash.put(integerRedisTemplate.opsForHash(), key, thumbingNumHashKey, thumbs);
        if(ArrayUtil.isNotEmpty(roast.getThumbUserIds())){
            Long[] userIds = new Long[roast.getThumbUserIds().size()];
            RedisUtilsForSet.add(longRedisTemplate.opsForSet(), thumbingUserIds+roast.getId(), roast.getThumbUserIds().toArray(userIds));
        }
        RedisUtilsForHash.put(roastRedisTemplate.opsForHash(), key, detailsHashKey, roast);
        setRoastExpireTime(roast.getId());
    }

    /**
     * 点赞
     * @param id
     * @param userId
     */
    public void changeThumbs(String id, Long userId){
        if(!existThumbingUser(id) || !RedisUtilsForSet.isExist(longRedisTemplate.opsForSet(), thumbingUserIds+id, userId)) {
            RedisUtilsForSet.add(longRedisTemplate.opsForSet(), thumbingUserIds+id, userId);
            RedisUtilsForHash.increment(integerRedisTemplate.opsForHash(), infoRoast+id, thumbingNumHashKey, 1);
            RedisUtilsForSet.add(stringRedisTemplate.opsForSet(), thumbingChanged+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), thumbingChangedSeq), id);
            setRoastExpireTime(id);
        }
    }

    public void removeInCollection(String id){
        RedisUtilsForList.remove(stringRedisTemplate.opsForList(), roastList, 1, id);
        RedisUtilsForSet.remove(stringRedisTemplate.opsForSet(), roastSet, id);
    }

    public void removeRoast(String id){
        removeInCollection(id);
        RedisUtils.remove(stringRedisTemplate, infoRoast+id);
    }

    /**
     * 获取前一阶段所有点赞数发生变化的吐槽
     * @return
     */
    public Set<String> getAndClearThumbChangedSet() {
        Integer seq = RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), thumbingChangedSeq);
        if (seq < Integer.MAX_VALUE) {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, seq + 1);
        } else {
            RedisUtilsForValue.set(integerRedisTemplate.opsForValue(), thumbingChangedSeq, 0);
        }
        if (RedisUtils.hasKey(longRedisTemplate, thumbingChanged + seq)) {
            Set<String> set = RedisUtilsForSet.members(stringRedisTemplate.opsForSet(), thumbingChanged + seq);
            RedisUtils.remove(longRedisTemplate, thumbingChanged + seq);
            return set;
        } else {
            return null;
        }
    }

}
