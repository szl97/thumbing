package com.thumbing.contentserver.lockoperation;

import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.cache.ArticleCache;
import com.thumbing.contentserver.dto.input.ArticleIdInput;
import com.thumbing.contentserver.dto.input.FetchArticleInput;
import com.thumbing.contentserver.dto.output.ArticleDto;
import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.dto.output.PageResultDto;
import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.entity.mongo.content.ArticleContent;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.repository.mongo.content.IArticleContentRepository;
import com.thumbing.shared.repository.mongo.content.IArticleRepository;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/24 18:00
 */
@Component
@Transactional(propagation = Propagation.NESTED)
public class ArticleLockOperation {
    @Autowired
    private IArticleRepository articleRepository;
    @Autowired
    private IArticleContentRepository contentRepository;
    @Autowired
    private ArticleCache articleCache;
    @Autowired
    private Mapper mapper;

    @AccessLock(value = "com.thumbing.shared.entity.mongo.content.ArticleContent",
            className = "com.thumbing.contentserver.dto.input.ArticleIdInput",
            fields = {
                    "getId"})
    public String getArticleContent(ArticleIdInput idInput){
        ArticleContent articleContent = contentRepository.findByArticleId(idInput.getId()).orElseThrow(()->new BusinessException("文章内容不存在"));
        articleCache.storeContent(idInput.getId(), articleContent.getContent());
        return articleContent.getContent();
    }

    @AccessLock(value = "com.thumbing.shared.entity.mongo.content.Article",
            className = "com.thumbing.contentserver.dto.input.FetchArticleInput",
            fields = {
                    "getPosition"})
    public PageResultDto<ArticleDto> getArticlePage(FetchArticleInput input){
        Sort sort = Sort.by(Sort.Direction.DESC, MongoCreationEntity.Fields.createTime);
        Page<Article> page = articleRepository.findAllByIsDelete(0, PageRequest.of(input.getPageNumber() - 1, input.getPageSize(), sort));
        List<Article> list = page.toList();
        list.stream().forEach(
             article -> {
                 articleCache.addArticleWhenInitialize(article);
             }
        );
        return DozerUtils.mapToPagedResultDtoSync(mapper, page, ArticleDto.class);
    }

    @AccessLock(value = "com.thumbing.shared.entity.mongo.content.Article",
            className = "com.thumbing.contentserver.dto.input.ArticleIdInput",
            fields = {
                    "getId"})
    public Article getArticle(ArticleIdInput idInput){
        Article article = articleRepository.findByIdAndIsDelete(idInput.getId(), 0).orElseThrow(()->new BusinessException("文章不存在"));
        articleCache.storeArticle(article);
        return article;
    }

    @AccessLock(value = {"com.thumbing.shared.entity.mongo.content.Article", "com.thumbing.shared.entity.mongo.content.ArticleContent"},
            className = "com.thumbing.contentserver.dto.input.ArticleIdInput",
            fields = {
                    "getId"})
    public Boolean deleteArticle(ArticleIdInput idInput){
        articleRepository.updateIsDeleteById(idInput.getId());
        if(articleCache.existArticleInfo(idInput.getId())){
            articleCache.removeArticle(idInput.getId());
        }
        return true;
    }
}
