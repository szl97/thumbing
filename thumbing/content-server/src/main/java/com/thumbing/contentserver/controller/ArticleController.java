package com.thumbing.contentserver.controller;

import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.service.impl.ArticleService;
import com.thumbing.shared.annotation.AllowAnonymous;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.shared.dto.output.DocumentDto;
import com.thumbing.shared.entity.mongo.common.NickUser;
import com.thumbing.shared.entity.mongo.content.Article;
import com.thumbing.shared.exception.BusinessException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/3 18:09
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/article")
public class ArticleController extends ThumbingBaseController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private Mapper dozermapper;

    @ApiOperation("发表文章")
    @AllowAnonymous
    @RequestMapping(value = "publish", method = RequestMethod.GET)
    public DocumentDto publishArticle(){
        Article article = new Article();
        article.setTitle("test");
        article.setContent("asdaasdaads");
        Set<NickUser> set = new HashSet<>();
        set.add(new NickUser(1l,"asadas"));
        set.add(new NickUser(2l,"asasasdaas"));
        set.add(new NickUser(3l,"asasaswwf3"));
        set.add(new NickUser(4l,"asasasxxxq"));
        article.setNickUsers(set);
        Article s = articleService.save(article);
        return dozermapper.map(s, DocumentDto.class);
    }

    @ApiOperation("获取文章")
    @AllowAnonymous
    @RequestMapping(value = "fetch", method = RequestMethod.GET)
    public DocumentDto FetchArticle(String  id) throws BusinessException {
        Article a = articleService.findById(id).orElseThrow(()->new BusinessException("不存在"));
        return dozermapper.map(a, DocumentDto.class);
    }

    @ApiOperation("删除文章")
    @AllowAnonymous
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Boolean deleteArticle(String id) throws BusinessException {
        articleService.deleteById(id);
        return true;
    }
}
