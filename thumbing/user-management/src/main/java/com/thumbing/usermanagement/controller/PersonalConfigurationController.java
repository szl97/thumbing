package com.thumbing.usermanagement.controller;

import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.usermanagement.dto.output.PersonalConfigurationDto;
import com.thumbing.usermanagement.service.IPersonalConfiguration;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 10:24
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/personalConfiguration")
public class PersonalConfigurationController extends ThumbingBaseController {
    @Autowired
    IPersonalConfiguration personalConfiguration;

    @ApiOperation("获取个人信息的系统配置选项")
    @RequestMapping(method = RequestMethod.GET)
    @Authorize(PermissionConstants.REGISTER)
    public PersonalConfigurationDto get(){
        return personalConfiguration.get();
    }
}
