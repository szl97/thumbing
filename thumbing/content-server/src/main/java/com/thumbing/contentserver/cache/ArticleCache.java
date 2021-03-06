package com.thumbing.contentserver.cache;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.utils.redis.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * @Date: 2020/8/20 8:59
 */
@Component
public class ArticleCache {
    @Autowired
    private RedisTemplate<String, Article> articleRedisTemplate;
    @Autowired
    private RedisTemplate<String, Integer> integerRedisTemplate;
    @Autowired
    private RedisTemplate<String, Long> longRedisTemplate;
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String,String> stringRedisTemplate;
    private final short expireDay = 30;
    public final static int maxLength = 10000;
    //key
    //list
    private final String articleList = CacheKeyConstants.ARTICLE_LIST;
    //hash
    private final String infoArticle = CacheKeyConstants.INFO_ARTICLE;
    private final String nickNameHashKey = CacheKeyConstants.NICK_NAME_SEQUENCE;
    private final String thumbingNumHashKey = CacheKeyConstants.THUMBING_NUM;
    private final String commentsNumHashKey = CacheKeyConstants.COMMENTS_NUM;
    private final String detailsHashKey = CacheKeyConstants.DETAILS;
    private final String abstractsHashKey = CacheKeyConstants.ABSTRACTS;
    private final String contentHashKey = CacheKeyConstants.CONTENT;
    //set
    private final String thumbingUserIds = CacheKeyConstants.ARTICLE_THUMBING_USER_SET;
    //changed
    private final String thumbingChanged = CacheKeyConstants.ARTICLE_CHANGED_THUMBING_NUM;
    private final String commentsChanged = CacheKeyConstants.ARTICLE_CHANGED_COMMENTS_NUM;
    private final String contentChanged = CacheKeyConstants.ARTICLE_CHANGED_CONTENT;
    //string
    //seq
    private final String thumbingChangedSeq = CacheKeyConstants.ARTICLE_CHANGED_THUMBING_SEQ;
    private final String commentsChangedSeq = CacheKeyConstants.ARTICLE_CHANGED_COMMENTS_SEQ;
    private final String contentChangedSeq = CacheKeyConstants.ARTICLE_CHANGED_CONTENT_SEQ;

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
     * 获取文章列表
     * @param from
     * @param end
     * @return
     */
    public List<Article> getArticles(int from, int end){
        List<String> ids = RedisUtilsForList.get(stringRedisTemplate.opsForList(), articleList, from, end);
        return ids.stream().map(id-> getArticle(id)).collect(Collectors.toList());
    }

    public Article getArticle(String id){
        String key = infoArticle+id;
        Article article = RedisUtilsForHash.get(articleRedisTemplate.opsForHash(), key, detailsHashKey);
        if(article != null){
            Integer thumbingNum = RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), key, thumbingNumHashKey);
            Integer commentsNum = RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), key, commentsNumHashKey);
            String abstracts = RedisUtilsForHash.get(stringRedisTemplate.opsForHash(), key, abstractsHashKey);
            Set<Long> userIds = RedisUtilsForSet.members(longRedisTemplate.opsForSet(), thumbingUserIds+id);
            article.setThumbingNum(thumbingNum);
            article.setCommentsNum(commentsNum);
            article.setAbstracts(abstracts);
            article.setThumbUserIds(userIds);
        }
        return article;
    }

    /**
     * 获取文章列表的长度
     * @return
     */
    public Long sizeOfArticleList(){
        return RedisUtilsForList.size(stringRedisTemplate.opsForList(), articleList);
    }

    /**
     * 获取文章内容
     * @param id
     * @return
     */
    public String getContent(String id){
        return RedisUtilsForHash.get(stringRedisTemplate.opsForHash(), infoArticle+id, contentHashKey);
    }

    /**
     * 获取文章的固定信息
     * @param id
     * @return
     */
    public Article getArticleNoChangedInfo(String id){
        return RedisUtilsForHash.get(articleRedisTemplate.opsForHash(), infoArticle+id, detailsHashKey);
    }

    /**
     * 获取评论数
     * @param id
     * @return
     */
    public int getArticleCommentsNum(String id){
        Integer i = RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), infoArticle+id, commentsNumHashKey);
        return i == null ? 0 : i;
    }

    /**
     * 获取文章的下一个匿名昵称
     * @param id
     * @return
     */
    public Integer getNickNameSeq(String id){
        Long seq = RedisUtilsForHash.increment(stringRedisTemplate.opsForHash(), infoArticle+id, nickNameHashKey, 1);
        return seq == null ? null : Integer.parseInt(seq.toString()) - 1;
    }

    /**
     * 获取文章的评论数和点赞数
     * @param id
     * @return
     */
    public List<Integer> getCommentsNumAndThumbsNum(String id){
        List<String> keys = new ArrayList<>();
        keys.add(commentsNumHashKey);
        keys.add(thumbingNumHashKey);
        return RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), infoArticle+id, keys);
    }

    /**
     * 检查文章列表是否存在
     * @return
     */
    public Boolean existArticleList(){
        return RedisUtils.hasKey(stringRedisTemplate, articleList);
    }

    /**
     * 检查文章信息是否存在
     * @param id
     * @return
     */
    public Boolean existArticleInfo(String id){
        return RedisUtils.hasKey(longRedisTemplate, infoArticle+id);
    }

    /**
     * 检查文章内容是否存在
     * @param id
     * @return
     */
    public Boolean existArticleContent(String id){
        return RedisUtilsForHash.hasKey(stringRedisTemplate.opsForHash(), infoArticle+id, contentHashKey);
    }

    /**
     * 文章点赞数是否存在
     * @param id
     * @return
     */
    public Boolean existArticleThumbsNum(String id){
        return RedisUtilsForHash.hasKey(integerRedisTemplate.opsForHash(), infoArticle+id, thumbingNumHashKey);
    }

    /**
     * 检查文章的点赞用户是否存在
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
     * 获取点赞数
     * @param id
     * @return
     */
    public Integer getThumbNum(String id){
        return RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), infoArticle+id, thumbingNumHashKey);
    }

    /**
     * 设置key的过期时间
     * @param id
     */
    public void setArticleExpireTime(String id){
        RedisUtils.expire(stringRedisTemplate, infoArticle+id, expireDay, TimeUnit.DAYS);
        if(existThumbingUser(id)){
            RedisUtils.expire(longRedisTemplate, thumbingUserIds+id, expireDay, TimeUnit.DAYS);
        }
    }

    /**
     * 添加文章
     * @param article
     * @param content
     */
    public void addArticle(Article article, String content){
        storeArticle(article, content);
        Long size = RedisUtilsForList.rightPush(stringRedisTemplate.opsForList(), articleList, article.getId());
        if(size > maxLength){
            RedisUtilsForList.clearAndPersist(stringRedisTemplate.opsForList(), articleList, maxLength-size, -1);
        }
    }

    /**
     * 添加文章
     * @param article
     */
    public void addArticleWhenInitialize(Article article){
        storeArticle(article);
        Long size = RedisUtilsForList.leftPush(stringRedisTemplate.opsForList(), articleList, article.getId());
        if(size > maxLength){
            RedisUtilsForList.clearAndPersist(stringRedisTemplate.opsForList(), articleList, maxLength-size, -1);
        }
    }

    /**
     * 在list的指定位置存储文章
     * @param article
     * @param position
     */
    public void storeArticleInList(Article article, int position){
        storeArticle(article);
        if(existArticleInfo(article.getId())) {
            RedisUtilsForList.setListValue(stringRedisTemplate.opsForList(), articleList, position, article.getId());
        }
    }

    /**
     * 将article存入redis
     * @param article
     */
    public void storeArticle(Article article){
        storeArticle(article, "");
    }

    /**
     * 将article存入redis
     * @param article
     */
    public void storeArticle(Article article, String content){
        String key = infoArticle+article.getId();
        String abstracts = article.getAbstracts();
        Map<String,String> stringMap = new HashMap<>();
        if(StringUtils.isNotBlank(content)) {
            stringMap.put(contentHashKey, content);
        }
        stringMap.put(abstractsHashKey, abstracts);
        RedisUtilsForHash.put(stringRedisTemplate.opsForHash(), key, stringMap);
        Integer thumbs = article.getThumbingNum();
        Integer comments = article.getCommentsNum();
        thumbs = thumbs == null ? 0 : thumbs;
        comments = comments == null ? 0 : comments;
        Map<String, Integer> integerMap = new HashMap<>();
        integerMap.put(thumbingNumHashKey, thumbs);
        integerMap.put(commentsNumHashKey, comments);
        integerMap.put(nickNameHashKey, article.getNickNameSequence());
        RedisUtilsForHash.put(integerRedisTemplate.opsForHash(), key, integerMap);
        if(CollectionUtils.isNotEmpty(article.getThumbUserIds())){
            Long[] userIds = new Long[article.getThumbUserIds().size()];
            RedisUtilsForSet.add(longRedisTemplate.opsForSet(), thumbingUserIds+article.getId(), article.getThumbUserIds().toArray(userIds));
        }
        article.setAbstracts(null);
        RedisUtilsForHash.put(articleRedisTemplate.opsForHash(), key, detailsHashKey, article);
        setArticleExpireTime(article.getId());
    }

    /**
     * 点赞或取消点赞
     * @param id
     * @param add
     * @param userId
     */
    public void changeThumbs(String id, boolean add, Long userId){
        if(!existThumbingUser(id) || (RedisUtilsForSet.isExist(longRedisTemplate.opsForSet(), thumbingUserIds+id, userId)^add)) {
            Long l = add ?  RedisUtilsForSet.add(longRedisTemplate.opsForSet(), thumbingUserIds+id, userId) : RedisUtilsForSet.remove(longRedisTemplate.opsForSet(), thumbingUserIds+id, userId);
            RedisUtilsForHash.increment(integerRedisTemplate.opsForHash(), infoArticle+id, thumbingNumHashKey, add?1:-1);
            RedisUtilsForSet.add(stringRedisTemplate.opsForSet(), thumbingChanged+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), thumbingChangedSeq), id);
            setArticleExpireTime(id);
        }
    }

    /**
     * 新增评论
     * @param id
     */
    public void addComments(String id){
        RedisUtilsForHash.increment(integerRedisTemplate.opsForHash(), infoArticle+id, commentsNumHashKey, 1);
        RedisUtilsForSet.add(stringRedisTemplate.opsForSet(), commentsChanged+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), commentsChangedSeq), id);
        setArticleExpireTime(id);
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
        stringMap.put(abstractsHashKey, abstracts);
        RedisUtilsForHash.put(stringRedisTemplate.opsForHash(), infoArticle+id, stringMap);
    }

    public void removeInList(String id){
        RedisUtilsForList.remove(stringRedisTemplate.opsForList(), articleList, 1, id);
    }
    public void removeArticle(String id){
        removeInList(id);
        RedisUtils.remove(stringRedisTemplate, infoArticle+id);
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
        stringMap.put(abstractsHashKey, abstracts);
        RedisUtilsForHash.put(stringRedisTemplate.opsForHash(), infoArticle+id, stringMap);
        RedisUtilsForSet.add(stringRedisTemplate.opsForSet(), contentChanged+RedisUtilsForValue.get(integerRedisTemplate.opsForValue(), contentChangedSeq), id);
        setArticleExpireTime(id);
    }

    /**
     * 获取前一阶段所有点赞数或评论数发生改变的文章
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
     * 获取当前的匿名昵称序号
     */
    public int getCurrentNickNameSeq(String id){
        return RedisUtilsForHash.get(integerRedisTemplate.opsForHash(), infoArticle+id, nickNameHashKey);
    }

    /**
     * 获取前一阶段所有内容发生改变的文章
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
