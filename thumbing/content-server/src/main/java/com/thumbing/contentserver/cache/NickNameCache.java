package com.thumbing.contentserver.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtils;
import com.thumbing.shared.utils.redis.RedisUtilsForHash;
import com.thumbing.shared.utils.redis.RedisUtilsForList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/20 10:50
 */
@Component
public class NickNameCache {
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;
    private final short expireDay = 30;
    //key
    //list
    private final String nickNameList = CacheKeyConstants.NICK_NAME_LIST;
    //hash
    private final String userNickNameArticle = CacheKeyConstants.USER_NICK_NAME_ARTICLE;
    private final String userNickNameMoments = CacheKeyConstants.USER_NICK_NAME_MOMENTS;

    /**
     * 根据sequence获取nick name
     * @param Sequence
     * @return
     */
    public String getNickName(int Sequence){
        return RedisUtilsForList.get(redisTemplate.opsForList(), nickNameList, Sequence);
    }

    /**
     * 检查list是否存在
     * @return
     */
    public Boolean nickNameListExist(){
        return RedisUtils.hasKey(redisTemplate, nickNameList);
    }

    /**
     * 添加list
     * @param nickNames
     */
    public void setNickNameList(List<String> nickNames){
        RedisUtilsForList.leftPush(redisTemplate.opsForList(), nickNameList, nickNames);
    }

    /**
     * 检查文章的nick name是否在缓存中
     * @param articleId
     * @return
     */
    public Boolean userNickNameArticleExist(String articleId){
        return RedisUtils.hasKey(redisTemplate, userNickNameArticle+articleId);
    }

    /**
     * 检查帖子的nick name是否在缓存中
     * @param momentsId
     * @return
     */
    public Boolean userNickNameMomentsExist(String momentsId){
        return RedisUtils.hasKey(redisTemplate, userNickNameMoments+momentsId);
    }

    /**
     * 获取用户在此文章下的nick name
     * @param articleId
     * @param userId
     * @return
     */
    public String getUserNickNameArticle(String articleId, Long userId){
        String name = RedisUtilsForHash.get(redisTemplate.opsForHash(), userNickNameArticle+articleId, userId.toString());
        if(StringUtils.isNotBlank(name)){
            setArticleExpireTime(articleId);
        }
        return name;
    }

    /**
     * 获取用户在此帖子下的nick name
     * @param momentsId
     * @param userId
     * @return
     */
    public String getUserNickNameMoments(String momentsId, Long userId){
        String name = RedisUtilsForHash.get(redisTemplate.opsForHash(), userNickNameMoments+momentsId, userId.toString());
        if(StringUtils.isNotBlank(name)){
            setMomentsExpireTime(momentsId);
        }
        return name;
    }

    /**
     * 保存用户在对应文章下的名字
     * @param articleId
     * @param userId
     * @param name
     */
    public void setUserNickNameArticle(String articleId, Long userId, String name){
        RedisUtilsForHash.put(redisTemplate.opsForHash(), userNickNameArticle+articleId, userId.toString(), name);
    }

    /**
     * 保存用户在对应帖子下的名字
     * @param momentsId
     * @param userId
     * @param name
     */
    public void setUserNickNameMoments(String momentsId, Long userId, String name){
        RedisUtilsForHash.put(redisTemplate.opsForHash(), userNickNameMoments+momentsId, userId.toString(), name);
    }

    /**
     * 设置文章下用户昵称的过期时间
     * @param articleId
     */
    public void setArticleExpireTime(String articleId){
        RedisUtils.expire(redisTemplate, userNickNameArticle+articleId, expireDay, TimeUnit.DAYS);
    }

    /**
     * 设置帖子下用户昵称的过期时间
     * @param momentsId
     */
    public void setMomentsExpireTime(String momentsId){
        RedisUtils.expire(redisTemplate, userNickNameMoments+momentsId, expireDay, TimeUnit.DAYS);
    }
}
