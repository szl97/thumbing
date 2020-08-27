package com.thumbing.contentserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.MomentsDto;
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
    PageResultDto<MomentsDto> fetchMoments(FetchMomentsInput input, UserContext context);

    /**
     * 获取帖子
     * @param input
     * @return
     */
    MomentsDto getMoments(MomentsIdInput input);

    /**
     * 发表帖子
     * @param input
     * @param context
     * @return
     * @throws JsonProcessingException
     */
    Boolean publishMoments(PublishMomentsInput input, UserContext context);

    /**
     * 删除帖子
     * @param input
     * @param context
     * @return
     */
    Boolean deleteMoments(MomentsIdInput input, UserContext context);

    /**
     * 点赞
     * @param input
     * @param context
     * @return
     */
    Boolean thumbMoments(ThumbMomentsInput input, UserContext context);

    /**
     * 修改帖子
     * @param input
     * @param context
     * @return
     */
    Boolean updateMoments(UpdateMomentsInput input, UserContext context);

    /**
     * 获取自己发布的帖子
     * @param input
     * @param context
     * @return
     */
    PageResultDto<MomentsDto> getMine(FetchMomentsInput input, UserContext context);
}
