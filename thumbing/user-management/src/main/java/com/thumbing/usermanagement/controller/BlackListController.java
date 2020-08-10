package com.thumbing.usermanagement.controller;

import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.usermanagement.dto.input.BlackListAddInput;
import com.thumbing.usermanagement.dto.input.BlackListRemoveInput;
import com.thumbing.usermanagement.dto.output.BlackListDto;
import com.thumbing.usermanagement.service.IBlackListService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 11:48
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/blackList")
public class BlackListController extends ThumbingBaseController {
    @Autowired
    IBlackListService blackListService;

    @ApiOperation("获取黑名单列表")
    @RequestMapping(method = RequestMethod.GET)
    @Authorize(PermissionConstants.REGISTER)
    List<BlackListDto> getAllBlackList(){
        return blackListService.getAllBlackList(getCurrentUser());
    }

    @ApiOperation("添加到黑名单列表")
    @RequestMapping(method = RequestMethod.POST)
    @Authorize(PermissionConstants.REGISTER)
    Boolean addToBlackList(@RequestBody BlackListAddInput input){
        return blackListService.addToBlackList(getCurrentUser(), input);
    }

    @ApiOperation("从黑名单列表移除")
    @RequestMapping(method = RequestMethod.DELETE)
    @Authorize(PermissionConstants.REGISTER)
    Boolean removeInBlackList(@RequestBody BlackListRemoveInput input){
        return blackListService.removeInBlackList(getCurrentUser(), input);
    }
}
