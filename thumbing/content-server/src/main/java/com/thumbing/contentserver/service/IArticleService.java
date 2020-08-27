package com.thumbing.contentserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.ArticleDto;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;

import java.io.IOException;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 17:17
 */
public interface IArticleService {
    /**
     * 获取文章列表
     * @param input
     * @param context
     * @return
     */
    PageResultDto<ArticleDto> fetchArticles(FetchArticleInput input, UserContext context);

    /**
     * 发表文章
     * @param input
     * @param context
     * @return
     * @throws JsonProcessingException
     */
    Boolean publishArticle(PublishArticleInput input, UserContext context);

    /**
     * 获取文章内容
     * @param input
     * @return
     */
    String getArticleContent(ArticleIdInput input);

    /**
     * 获取文章
     * @param input
     * @return
     */
    ArticleDto getArticle(ArticleIdInput input);

    /**
     * 删除文章
     * @param input
     * @param context
     * @return
     */
    Boolean deleteArticle(ArticleIdInput input, UserContext context);

    /**
     * 点赞
     * @param input
     * @param context
     * @return
     */
    Boolean thumbArticle(ThumbArticleInput input, UserContext context);

    /**
     * 修改文章
     * @param input
     * @param context
     * @return
     */
    Boolean updateArticle(UpdateArticleInput input, UserContext context);

    /**
     * 获取自己发布的文章
     * @param input
     * @param context
     * @return
     */
    PageResultDto<ArticleDto> getMine(FetchArticleInput input, UserContext context);
}
