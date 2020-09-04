package com.thumbing.usermanagement.controller;

import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.usermanagement.dto.input.RelationApplyHandlerInput;
import com.thumbing.usermanagement.dto.input.RelationApplyInput;
import com.thumbing.usermanagement.dto.input.RelationRemoveInput;
import com.thumbing.usermanagement.dto.output.RelationApplyDto;
import com.thumbing.usermanagement.dto.output.RelationDto;
import com.thumbing.usermanagement.service.IRelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 11:54
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/relation")
public class RelationController extends ThumbingBaseController {
    @Autowired
    private IRelationService relationService;

    @ApiOperation("获取好友列表")
    @RequestMapping(method = RequestMethod.GET)
    @Authorize(PermissionConstants.REGISTER)
    public List<RelationDto> getAllRelation(){
        return relationService.getAllRelation(getCurrentUser());
    }

    @ApiOperation("获取所有好友申请")
    @RequestMapping(value = "/applyInfo", method = RequestMethod.GET)
    @Authorize(PermissionConstants.REGISTER)
    public List<RelationApplyDto> getAllRelationApply(){
        return relationService.getAllRelationApply(getCurrentUser());
    }

    @ApiOperation("申请添加好友")
    @RequestMapping(value = "/apply", method = RequestMethod.PUT)
    @Authorize(PermissionConstants.REGISTER)
    public Boolean applyRelation(@RequestBody RelationApplyInput input){
        return relationService.applyRelation(getCurrentUser(), input);
    }

    @ApiOperation("处理好友请求")
    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    @Authorize(PermissionConstants.REGISTER)
    public Boolean handleRelationApply(@RequestBody RelationApplyHandlerInput input){
        return relationService.handleRelationApply(getCurrentUser(), input);
    }

    @ApiOperation("删除好友")
    @RequestMapping(method = RequestMethod.DELETE)
    @Authorize(PermissionConstants.REGISTER)
    public Boolean removeRelation(RelationRemoveInput input){
        return relationService.removeRelation(getCurrentUser(), input);
    }
}
