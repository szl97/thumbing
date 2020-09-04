package com.thumbing.contentserver.controller;

import com.thumbing.contentserver.dto.input.CommentIdInput;
import com.thumbing.contentserver.dto.input.CommentInput;
import com.thumbing.contentserver.dto.input.FetchCommentInput;
import com.thumbing.contentserver.dto.input.ThumbCommentInput;
import com.thumbing.contentserver.dto.output.CommentDto;
import com.thumbing.contentserver.service.ICommentService;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 16:53
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/comment")
public class CommentController extends ThumbingBaseController {
    @Autowired
    private ICommentService commentService;

    @ApiOperation("发表评论")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "publish", method = RequestMethod.POST)
    public Boolean publishComment(@RequestBody @Valid CommentInput input){
        return commentService.publishComment(input, getCurrentUser());
    }

    @ApiOperation("获取所有评论")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "fetch", method = RequestMethod.GET)
    List<CommentDto> fetchComments(@Valid FetchCommentInput input){
        return commentService.fetchComments(input, getCurrentUser());
    }

    @ApiOperation("删除评论")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Boolean deleteComment(@Valid CommentIdInput input) {
        return commentService.deleteComment(input, getCurrentUser());
    }

    @ApiOperation("点赞")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "thumb", method = RequestMethod.PATCH)
    public Boolean thumbComment(@RequestBody @Valid ThumbCommentInput input){
        return commentService.thumbComment(input, getCurrentUser());
    }
}
