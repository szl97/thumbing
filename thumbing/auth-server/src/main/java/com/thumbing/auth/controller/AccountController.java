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
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/changePassword", method = RequestMethod.PATCH)
    public AccountDto changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
        return accountService.changePassword(changePasswordRequest);
    }

    @ApiOperation("检查唯一性")
    @AllowAnonymous
    @RequestMapping(value = "/checkUnique", method = RequestMethod.POST)
    public Boolean checkUnique(@Valid @RequestBody CheckUniqueInput checkUniqueInput){
        return accountService.checkUnique(checkUniqueInput);
    }

    @ApiOperation("模拟注册短信发送")
    @AllowAnonymous
    @RequestMapping(value = "/registerSms", method = RequestMethod.GET)
    public Boolean registerSms(@RequestParam String phoneNum){
        return accountService.simulateRegisterSms(phoneNum);
    }

    @ApiOperation("模拟修改密码短信发送")
    @AllowAnonymous
    @RequestMapping(value = "/changerSms", method = RequestMethod.GET)
    public Boolean changerSms(@RequestParam String phoneNum){
        return accountService.simulateChangePasswordSms(phoneNum);
    }
}
