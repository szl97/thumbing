package com.thumbing.usermanagement.service;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.usermanagement.dto.input.BlackListAddInput;
import com.thumbing.usermanagement.dto.input.BlackListRemoveInput;
import com.thumbing.usermanagement.dto.output.BlackListDto;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 11:41
 */
public interface IBlackListService {

    BlackListDto addToBlackList(UserContext context, BlackListAddInput input);

    Boolean removeInBlackList(UserContext context, BlackListRemoveInput input);
}
