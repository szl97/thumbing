package com.thumbing.contentserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.ArticleDto;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 17:17
 */
public interface IArticleService {
    PageResultDto<ArticleDto> fetchArticles(FetchArticleInput input, UserContext context);

    Boolean publishArticle(PublishArticleInput input, UserContext context) throws JsonProcessingException;

    String getArticleContent(ArticleIdInput input);

    Boolean deleteArticle(ArticleIdInput input);

    Boolean thumbArticle(ThumbArticleInput input);

    Boolean updateArticle(UpdateArticleInput input);
}
