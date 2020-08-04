package com.thumbing.auth.controller;

import com.thumbing.auth.dto.input.ChangePasswordRequest;
import com.thumbing.auth.dto.input.CheckUniqueInput;
import com.thumbing.auth.dto.input.SignUpInput;
import com.thumbing.auth.dto.output.AccountDto;
import com.thumbing.auth.service.IAccountService;
import com.thumbing.shared.annotation.AllowAnonymous;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.controller.ThumbingBaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 20:52
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/account")
public class AccountController extends ThumbingBaseController {
    @Autowired
    private IAccountService accountService;

    @ApiOperation("注册")
    @AllowAnonymous
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public AccountDto register(@Valid @RequestBody SignUpInput signUpInput){
        return accountService.register(signUpInput);
    }

    @ApiOperation("更改密码")
    @AllowAnonymous
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public AccountDto changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
        return accountService.changePassword(changePasswordRequest);
    }

    @ApiOperation("检查唯一性")
    @AllowAnonymous
    @RequestMapping(value = "/checkUnique", method = RequestMethod.POST)
    public Boolean checkUnique(@Valid @RequestBody CheckUniqueInput checkUniqueInput){
        return accountService.checkUnique(checkUniqueInput);
    }
}
