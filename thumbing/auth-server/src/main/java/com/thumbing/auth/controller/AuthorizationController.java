package com.thumbing.auth.controller;

import com.thumbing.auth.dto.input.LoginRequest;
import com.thumbing.auth.service.impl.AuthService;
import com.thumbing.shared.annotation.AllowAnonymous;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.controller.ThumbingBaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 12:52
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/authorization")
public class AuthorizationController extends ThumbingBaseController {
    @Autowired
    private AuthService authService;

    @ApiOperation("登录")
    @AllowAnonymous
    @RequestMapping(value = "/getAuthorization", method = RequestMethod.POST)
    public String getAuthorization(@Valid @RequestBody LoginRequest input){
        return authService.getAuthorization(input);
    }

    @ApiOperation("认证")
    @AllowAnonymous
    @RequestMapping(value = "/checkAuthorization", method = RequestMethod.GET)
    public Boolean checkAuthorization(@RequestParam(name = "authorization", required = false) String authorization,
                             @RequestParam("applicationName") String applicationName,
                             @RequestParam("url") String url){
        return authService.auth(authorization, applicationName, url);
    }
}
