package com.thumbing.contentserver.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.cache.ArticleCache;
import com.thumbing.contentserver.config.ElasticSearchConfig;
import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.ArticleDto;
import com.thumbing.contentserver.elasticsearch.ElasticBaseEntity;
import com.thumbing.contentserver.elasticsearch.ElasticUtils;
import com.thumbing.contentserver.service.IArticleService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;
import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.entity.mongo.content.ArticleContent;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.repository.mongo.content.IArticleContentRepository;
import com.thumbing.shared.repository.mongo.content.IArticleRepository;
import com.thumbing.shared.service.impl.BaseMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    private Mapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PageResultDto<ArticleDto> fetchArticles(FetchArticleInput input, UserContext context) {
        return null;
    }

    @Override
    public Boolean publishArticle(PublishArticleInput input, UserContext context) throws JsonProcessingException {
        Article article = mapper.map(input, Article.class);
        article.setAbstracts(input.getContent().substring(0,100));
        article.setUserId(context.getId());
        article.setCreateTime(LocalDateTime.now());
        article = repository.save(article);
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(input.getContent());
        articleContent.setArticleId(article.getId());
        contentRepository.save(articleContent);
        articleCache.addArticle(article, input.getContent());
        ElasticBaseEntity elasticBaseEntity = new ElasticBaseEntity();
        elasticBaseEntity.setId(article.getId());
        elasticBaseEntity.setContent(article.getAbstracts());
        elasticBaseEntity.setName(ContentType.ARTICLE);
        elasticBaseEntity.setDateTime(article.getCreateTime());
        elasticBaseEntity.setTags(objectMapper.writeValueAsString(article.getTagIds()));
        elasticUtils.indexDoc(ElasticSearchConfig.indexName, objectMapper.writeValueAsString(elasticBaseEntity));
        return true;
    }

    @Override
    public String getArticleContent(ArticleIdInput input) {
        return null;
    }

    @Override
    public Boolean deleteArticle(ArticleIdInput input) {
        return null;
    }

    @Override
    public Boolean thumbArticle(ThumbArticleInput input) {
        return null;
    }

    @Override
    public Boolean updateArticle(UpdateArticleInput input) {
        return null;
    }
}
