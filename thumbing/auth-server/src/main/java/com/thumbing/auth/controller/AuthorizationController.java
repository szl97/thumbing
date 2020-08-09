package com.thumbing.auth.controller;

import com.thumbing.auth.service.impl.AuthService;
import com.thumbing.shared.annotation.AllowAnonymous;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.controller.ThumbingBaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("认证")
    @AllowAnonymous
    @RequestMapping(value = "/checkAuthorization", method = RequestMethod.GET)
    public Boolean checkAuthorization(@RequestParam(name = "authorization", required = false) String authorization,
                             @RequestParam("applicationName") String applicationName,
                             @RequestParam("url") String url){
        return authService.auth(authorization, applicationName, url);
    }
}
