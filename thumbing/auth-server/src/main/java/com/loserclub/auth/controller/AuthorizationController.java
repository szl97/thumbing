package com.loserclub.auth.controller;

import com.loserclub.auth.model.LoginRequest;
import com.loserclub.auth.service.AuthServiceImpl;
import com.loserclub.shared.annotation.AllowAnonymous;
import com.loserclub.shared.auth.model.UserContext;
import com.loserclub.shared.controller.LoserClubBaseController;
import com.loserclub.shared.exception.BusinessException;
import com.loserclub.shared.jwt.JwtTokenFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 12:52
 */
@RestController
@RequestMapping(value = "/authorization")
public class AuthorizationController extends LoserClubBaseController{
    @Autowired
    AuthServiceImpl authService;
    @Autowired
    private JwtTokenFactory jwtTokenFactory;
    @Autowired
    private AuthenticationManager authenticationManager;


    @ApiOperation("登录")
    @AllowAnonymous
    @RequestMapping(value = "/getAuthorization", method = RequestMethod.POST)
    public String getAuthorization(@Valid @RequestBody LoginRequest input){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());
        Authentication authResult = authenticationManager.authenticate(token);
        if (authResult.isAuthenticated()) {
            UserContext userContext = (UserContext) authResult.getPrincipal();
            return jwtTokenFactory.createJwtToken(userContext);
        } else {
            throw new BusinessException("帐户名或者密码错误");
        }
    }

    @ApiOperation("认证")
    @AllowAnonymous
    @RequestMapping(value = "checkAuthorization", method = RequestMethod.GET)
    public Boolean checkAuthorization(@RequestParam(name = "authorization", required = false) String authorization,
                             @RequestParam("applicationName") String applicationName,
                             @RequestParam("url") String url){
        return authService.auth(authorization, applicationName, url);
    }
}
