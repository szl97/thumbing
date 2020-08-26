package com.thumbing.contentserver.service;

import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.CommentDto;
import com.thumbing.shared.auth.model.UserContext;

import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-08-26 4:59
 */
public interface ICommentService {
    /**
     * 发表评论
     * @param input
     * @param context
     * @return
     */
    Boolean publishComment(CommentInput input, UserContext context);

    /**
     * 获取文章或帖子下的评论
     * @param input
     * @param context
     * @return
     */
    List<CommentDto> fetchComments(FetchCommentInput input, UserContext context);

    /**
     * 删除评论
     * @param input
     * @param context
     * @return
     */
    Boolean deleteComment(CommentIdInput input, UserContext context);

    /**
     * 点赞
     * @param input
     * @param context
     * @return
     */
    Boolean thumbComment(ThumbCommentInput input, UserContext context);
}
