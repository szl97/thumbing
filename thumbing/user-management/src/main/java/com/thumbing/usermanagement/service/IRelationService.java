package com.thumbing.usermanagement.service;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.usermanagement.dto.input.RelationApplyHandlerInput;
import com.thumbing.usermanagement.dto.input.RelationApplyInput;
import com.thumbing.usermanagement.dto.input.RelationRemoveInput;
import com.thumbing.usermanagement.dto.output.RelationApplyDto;
import com.thumbing.usermanagement.dto.output.RelationDto;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 11:35
 */
public interface IRelationService {
    /**
     * 申请添加好友
     * @param userContext
     * @param input
     * @return
     */
    Boolean applyRelation(UserContext userContext, RelationApplyInput input);
    /**
     * 处理好友请求
     * @param userContext
     * @param input
     * @return
     */
    Boolean handleRelationApply(UserContext userContext, RelationApplyHandlerInput input);
    /**
     * 删除好友
     * @param userContext
     * @param input
     * @return
     */
    Boolean removeRelation(UserContext userContext, RelationRemoveInput input);

    /**
     * 获取好友列表
     * @param userContext
     * @return
     */
    List<RelationDto> getAllRelation(UserContext userContext);

    /**
     * 获取好友申请列表
     * @param userContext
     * @return
     */
    List<RelationApplyDto> getAllRelationApply(UserContext userContext);
}
