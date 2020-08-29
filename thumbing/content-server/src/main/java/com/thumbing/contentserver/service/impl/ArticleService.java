package com.thumbing.contentserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.cache.ArticleCache;
import com.thumbing.contentserver.config.ElasticSearchConfig;
import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.ArticleDto;
import com.thumbing.contentserver.elasticsearch.ElasticBaseEntity;
import com.thumbing.contentserver.elasticsearch.ElasticUtils;
import com.thumbing.contentserver.lockoperation.ArticleLockOperation;
import com.thumbing.contentserver.service.IArticleService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;
import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.entity.mongo.content.ArticleContent;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.repository.mongo.content.IArticleContentRepository;
import com.thumbing.shared.repository.mongo.content.IArticleRepository;
import com.thumbing.shared.service.impl.BaseMongoService;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import com.thumbing.shared.utils.sensitiveword.SensitiveFilter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 17:20
 */
@Service
@Transactional
public class ArticleService extends BaseMongoService<Article, IArticleRepository> implements IArticleService {
    @Autowired
    private IArticleContentRepository contentRepository;
    @Autowired
    private ElasticUtils elasticUtils;
    @Autowired
    private ArticleCache articleCache;
    @Autowired
    private ArticleLockOperation lockOperation;
    @Autowired
    private Mapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PageResultDto<ArticleDto> fetchArticles(FetchArticleInput input, UserContext context) {
        if (articleCache.existArticleList() && input.getPosition() > 0) {
            int length = articleCache.sizeOfArticleList().intValue();
            if (length > 0) {
                int to = Math.min(length, input.getPosition());
                int from = Math.max(to - input.getPageSize(), 0);
                List<Article> articles = articleCache.getArticles(from, to);
                Collections.reverse(articles);
                List<ArticleDto> dtoList = DozerUtils.mapList(mapper, articles, ArticleDto.class);
                return new PageResultDto<>(ArticleCache.maxLength, dtoList, from - 1);
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public Boolean publishArticle(PublishArticleInput input, UserContext context) {
        SensitiveFilter filter = SensitiveFilter.DEFAULT;
        input.setContent(filter.filter(input.getContent(),'*'));
        Article article = mapper.map(input, Article.class);
        article.setAbstracts(input.getContent().substring(0,100));
        article.setNickNameSequence(0);
        article.setUserId(context.getId());
        article.setCreateTime(LocalDateTime.now());
        article = repository.save(article);
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(input.getContent());
        articleContent.setArticleId(article.getId());
        contentRepository.save(articleContent);
        articleCache.addArticle(article, input.getContent());
        ElasticBaseEntity elasticBaseEntity = new ElasticBaseEntity();
        elasticBaseEntity.setContentId(article.getId());
        elasticBaseEntity.setContent(article.getAbstracts());
        elasticBaseEntity.setName(ContentType.ARTICLE);
        elasticBaseEntity.setDateTime(article.getCreateTime());
        elasticBaseEntity.setTags(objectMapper.writeValueAsString(article.getTagIds()));
        elasticUtils.indexDoc(ElasticSearchConfig.indexName, objectMapper.writeValueAsString(elasticBaseEntity));
        return true;
    }

    @Override
    public String getArticleContent(ArticleIdInput input) {
        if (articleCache.existArticleContent(input.getId())) {
            return articleCache.getContent(input.getId());
        } else {
            String result = lockOperation.getArticleContent(input);
            if (result == null) return getArticleContent(input);
            return result;
        }
    }

    @Override
    public ArticleDto getArticle(ArticleIdInput input) {
        Article article = confirmArticleInRedis(input);
        String content = getArticleContent(input);
        ArticleDto articleDto = mapper.map(article, ArticleDto.class);
        articleDto.setContent(content);
        return articleDto;
    }

    @SneakyThrows
    @Override
    public Boolean deleteArticle(ArticleIdInput input, UserContext context){
        Article article;
        if(articleCache.existArticleInfo(input.getId())){
            article = articleCache.getArticleNoChangedInfo(input.getId());
        }
        else {
            article = repository.findByIdAndIsDelete(input.getId(), 0).orElseThrow(()->new BusinessException("文章不存在"));
        }
        if(!article.getUserId().equals(context.getId())) throw new BusinessException("当前用户无法删除");
        if(!lockOperation.deleteArticle(input)) return deleteArticle(input, context);
        elasticUtils.deleteDoc(ElasticSearchConfig.indexName, ContentType.ARTICLE.value(), input.getId());
        return true;
    }

    @Override
    public Boolean thumbArticle(ThumbArticleInput input, UserContext context) {
        confirmArticleThumbsInRedis(input);
        articleCache.changeThumbs(input.getId(), input.isAdd(), context.getId());
        //todo:发送到消息队列，record-server和data-center接收
        return true;
    }

    @SneakyThrows
    @Override
    public Boolean updateArticle(UpdateArticleInput input, UserContext context) {
        Article article = confirmArticleInRedis(input);
        if(!article.getUserId().equals(context.getId())) throw new BusinessException("当前用户无法修改");
        articleCache.changeContent(input.getId(), input.getContent());
        elasticUtils.updateDocContent(ElasticSearchConfig.indexName, ContentType.ARTICLE.value(), input.getId(), input.getContent().substring(0,100));
        return true;
    }

    private void confirmArticleThumbsInRedis(ArticleIdInput input) {
        if (!(articleCache.existThumbingUser(input.getId()) && articleCache.existArticleThumbsNum(input.getId()))) {
            if(lockOperation.getArticle(input) == null) confirmArticleThumbsInRedis(input);
        }
    }

    private Article confirmArticleInRedis(ArticleIdInput input) {
        if (articleCache.existArticleInfo(input.getId())) {
            return articleCache.getArticleNoChangedInfo(input.getId());
        } else {
            Article article = lockOperation.getArticle(input);
            if (article == null) confirmArticleInRedis(input);
            return article;
        }
    }
}
