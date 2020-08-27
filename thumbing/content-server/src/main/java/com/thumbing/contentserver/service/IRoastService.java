package com.thumbing.contentserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.contentserver.dto.input.FetchRoastInput;
import com.thumbing.contentserver.dto.input.PublishRoastInput;
import com.thumbing.contentserver.dto.input.RoastIdInput;
import com.thumbing.contentserver.dto.input.ThumbRoastInput;
import com.thumbing.contentserver.dto.output.RoastDto;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.dto.output.PageResultDto;
import com.thumbing.shared.entity.mongo.content.Roast;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 17:30
 */
public interface IRoastService {

    /**
     * 随机获取心情吐槽
     * @param context
     * @return
     */
    List<Roast> fetchRoasts(UserContext context);

    /**
     * 发表心情吐槽
     * @param input
     * @param context
     * @return
     * @throws JsonProcessingException
     */
    Boolean publishRoasts(PublishRoastInput input, UserContext context);

    /**
     * 删除心情吐槽
     * @param input
     * @param context
     * @return
     */
    Boolean deleteRoast(RoastIdInput input, UserContext context);

    /**
     * 点赞
     * @param input
     * @param context
     * @return
     */
    Boolean thumbRoast(ThumbRoastInput input, UserContext context);

    /**
     * 获取自己发布的心情吐槽
     * @param input
     * @param context
     * @return
     */
    PageResultDto<RoastDto> getMine(FetchRoastInput input, UserContext context);
}
