package com.thumbing.usermanagement.controller;

import com.thumbing.shared.annotation.AllowAnonymous;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.usermanagement.dto.input.PersonalEditInput;
import com.thumbing.usermanagement.dto.input.PersonalInput;
import com.thumbing.usermanagement.dto.input.UserInfoInput;
import com.thumbing.usermanagement.dto.output.PersonalDto;
import com.thumbing.usermanagement.service.IPersonalService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 11:42
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/personal")
public class PersonalController extends ThumbingBaseController {
    @Autowired
    private IPersonalService personalService;

    @ApiOperation("注册时调用创建用户信息")
    @RequestMapping(value = "/userInfo/register", method = RequestMethod.PUT)
    @AllowAnonymous
    public Boolean createPersonal(@RequestBody UserContext userContext){
        return personalService.createUserInfo(userContext);
    }

    @ApiOperation("创建用户信息")
    @RequestMapping(value = "/userInfo", method = RequestMethod.PUT)
    @Authorize(PermissionConstants.REGISTER)
    public Boolean createPersonal(@RequestBody UserInfoInput userInfoInput){
        return personalService.createUserInfo(getCurrentUser(), userInfoInput);
    }

    @ApiOperation("获取个人资料")
    @RequestMapping(method = RequestMethod.GET)
    @Authorize(PermissionConstants.REGISTER)
    public PersonalDto fetchPersonal(){
        return personalService.fetchPersonal(getCurrentUser());
    }

    @ApiOperation("创建个人资料")
    @RequestMapping(method = RequestMethod.PUT)
    @Authorize(PermissionConstants.REGISTER)
    public Boolean createPersonal(@RequestBody PersonalInput personalInput){
        return personalService.createPersonal(getCurrentUser(), personalInput);
    }

    @ApiOperation("修改个人资料")
    @RequestMapping(method = RequestMethod.POST)
    @Authorize(PermissionConstants.REGISTER)
    public PersonalDto updatePersonal(@RequestBody PersonalEditInput input){
        return personalService.updatePersonal(getCurrentUser(), input);
    }
}
