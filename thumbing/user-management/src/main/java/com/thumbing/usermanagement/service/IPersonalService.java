package com.thumbing.usermanagement.service;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.entity.sql.user.UserInfo;
import com.thumbing.usermanagement.dto.input.PersonalEditInput;
import com.thumbing.usermanagement.dto.input.PersonalInput;
import com.thumbing.usermanagement.dto.input.UserInfoInput;
import com.thumbing.usermanagement.dto.output.PersonalDto;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 15:47
 */
public interface IPersonalService {
    /**
     * 创建用户信息
     * @param userContext
     * @param input
     * @return
     */
    Boolean createUserInfo(UserContext userContext, UserInfoInput input);
    /**
     * 创建个人资料
     * @param personalInput
     * @param userContext
     * @return
     */
    Boolean createPersonal(UserContext userContext, PersonalInput personalInput);

    /**
     * 修改个人资料
     * @param input
     * @param userContext
     * @return
     */
    PersonalDto updatePersonal(UserContext userContext, PersonalEditInput input);

    /**
     * 获取个人资料
     * @param userContext
     * @return
     */
    PersonalDto fetchPersonal(UserContext userContext);
}
