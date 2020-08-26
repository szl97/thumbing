package com.thumbing.contentserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.ArticleDto;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 10:09
 */
public interface IMomentsService {
    /**
     * 获取帖子列表
     * @param input
     * @param context
     * @return
     */
    PageResultDto<ArticleDto> fetchArticles(FetchArticleInput input, UserContext context);

    /**
     * 获取帖子
     * @param input
     * @return
     */
    ArticleDto getMoments(MomentsIdInput input);

    /**
     * 发表帖子
     * @param input
     * @param context
     * @return
     * @throws JsonProcessingException
     */
    Boolean publishArticle(PublishArticleInput input, UserContext context) throws JsonProcessingException;

    /**
     * 删除帖子
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
     * 修改帖子
     * @param input
     * @param context
     * @return
     */
    Boolean updateArticle(UpdateArticleInput input, UserContext context);
}
