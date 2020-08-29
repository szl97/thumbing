package com.thumbing.contentserver.controller;

import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.MomentsDto;
import com.thumbing.contentserver.service.IMomentsService;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.shared.dto.output.PageResultDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 16:21
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/moments")
public class MomentsController extends ThumbingBaseController {
    @Autowired
    private IMomentsService momentsService;

    @ApiOperation("发表帖子")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "publish", method = RequestMethod.PUT)
    public Boolean publishMoments(@Valid PublishMomentsInput input){
        return momentsService.publishMoments(input, getCurrentUser());
    }

    @ApiOperation("获取帖子详情")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "getDetails", method = RequestMethod.GET)
    public MomentsDto getMoments(MomentsIdInput input){
        return momentsService.getMoments(input);
    }

    @ApiOperation("获取帖子列表")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "fetch", method = RequestMethod.GET)
    public PageResultDto<MomentsDto> fetchArticles(FetchMomentsInput input){
        return momentsService.fetchMoments(input, getCurrentUser());
    }

    @ApiOperation("删除帖子")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Boolean deleteMoments(MomentsIdInput input) {
        return momentsService.deleteMoments(input, getCurrentUser());
    }

    @ApiOperation("点赞")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "thumb", method = RequestMethod.POST)
    public Boolean thumbArticle(@Valid ThumbMomentsInput input){
        return momentsService.thumbMoments(input, getCurrentUser());
    }

    @ApiOperation("修改内容")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Boolean updateArticle(@Valid UpdateMomentsInput input){
        return momentsService.updateMoments(input, getCurrentUser());
    }

    @ApiOperation("获取自己发布的帖子")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "getMine", method = RequestMethod.GET)
    public PageResultDto<MomentsDto> getMine(FetchMomentsInput input){
        return momentsService.getMine(input, getCurrentUser());
    }
}
