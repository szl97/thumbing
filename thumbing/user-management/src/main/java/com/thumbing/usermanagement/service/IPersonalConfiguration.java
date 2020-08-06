package com.thumbing.usermanagement.service;

import com.thumbing.usermanagement.dto.output.PersonalConfigurationDto;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 16:09
 */
public interface IPersonalConfiguration {

    /**
     * 获取个人信息的系统配置
     * 包括兴趣爱好、职业、专业领域
     * @return
     */
    PersonalConfigurationDto get();
}
