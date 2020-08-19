package com.thumbing.contentserver.service;

import com.thumbing.contentserver.dto.input.FetchArticleInput;
import com.thumbing.contentserver.dto.input.PublishArticleInput;
import com.thumbing.contentserver.dto.output.ArticleDto;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 17:17
 */
public interface IArticleService {
    PageResultDto<ArticleDto> fetchArticles(FetchArticleInput input, UserContext context);

    ArticleDto publishArticle(PublishArticleInput input, UserContext context);
}
