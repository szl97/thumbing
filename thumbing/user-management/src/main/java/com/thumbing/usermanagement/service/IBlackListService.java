package com.thumbing.usermanagement.service;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.usermanagement.dto.input.BlackListAddInput;
import com.thumbing.usermanagement.dto.input.BlackListRemoveInput;
import com.thumbing.usermanagement.dto.output.BlackListDto;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 11:41
 */
public interface IBlackListService {

    /**
     * 添加到黑名单
     * @param context
     * @param input
     * @return
     */
    Boolean addToBlackList(UserContext context, BlackListAddInput input);

    /**
     * 从黑名单删除
     * @param context
     * @param input
     * @return
     */
    Boolean removeInBlackList(UserContext context, BlackListRemoveInput input);

    /**
     * 获取黑名单列表
     * @param context
     * @return
     */
    List<BlackListDto> getAllBlackList(UserContext context);
}
