package com.thumbing.contentserver.controller;

import com.thumbing.contentserver.dto.input.*;
import com.thumbing.contentserver.dto.output.ArticleDto;
import com.thumbing.contentserver.service.IArticleService;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.shared.dto.output.PageResultDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 18:09
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/article")
public class ArticleController extends ThumbingBaseController {
    @Autowired
    private IArticleService articleService;

    @ApiOperation("发表文章")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "publish", method = RequestMethod.POST)
    public Boolean publishArticle(@RequestBody @Valid PublishArticleInput input){
        return articleService.publishArticle(input, getCurrentUser());
    }

    @ApiOperation("获取文章内容")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "getContent", method = RequestMethod.GET)
    public String getArticleContent(ArticleIdInput input){
        return articleService.getArticleContent(input);
    }

    @ApiOperation("获取文章详情")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "getDetails", method = RequestMethod.GET)
    public ArticleDto getArticle(ArticleIdInput input){
        return articleService.getArticle(input);
    }

    @ApiOperation("获取文章列表")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "fetch", method = RequestMethod.GET)
    public PageResultDto<ArticleDto> fetchArticles(FetchArticleInput input){
        return articleService.fetchArticles(input, getCurrentUser());
    }

    @ApiOperation("删除文章")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Boolean deleteArticle(ArticleIdInput input) {
        return articleService.deleteArticle(input, getCurrentUser());
    }

    @ApiOperation("点赞")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "thumb", method = RequestMethod.PATCH)
    public Boolean thumbArticle(@RequestBody @Valid ThumbArticleInput input){
        return articleService.thumbArticle(input, getCurrentUser());
    }

    @ApiOperation("修改内容")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Boolean updateArticle(@RequestBody @Valid UpdateArticleInput input){
        return articleService.updateArticle(input, getCurrentUser());
    }

    @ApiOperation("获取自己发布的文章")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "getMine", method = RequestMethod.GET)
    public PageResultDto<ArticleDto> getMine(FetchArticleInput input){
        return articleService.getMine(input, getCurrentUser());
    }
}
