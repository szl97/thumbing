package com.thumbing.usermanagement.service;

import com.thumbing.shared.dto.input.UserInput;
import com.thumbing.shared.entity.sql.personal.Personal;
import com.thumbing.usermanagement.dto.input.PersonalInput;
import com.thumbing.usermanagement.dto.output.PersonalDto;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 15:47
 */
public interface IPersonalService {
    /**
     * 创建个人资料
     * @param personalInput
     * @return
     */
    PersonalDto createPersonal(PersonalInput personalInput);

    /**
     * 修改个人资料
     * @param personalEditInput
     * @return
     */
    PersonalDto updatePersonal(PersonalInput personalEditInput);

    /**
     * 获取个人资料
     * @return
     */
    PersonalDto fetchPersonal(UserInput userInput);
}
