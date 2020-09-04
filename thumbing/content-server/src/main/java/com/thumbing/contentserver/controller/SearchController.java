package com.thumbing.contentserver.controller;

import com.thumbing.contentserver.dto.input.SearchInput;
import com.thumbing.contentserver.dto.output.SearchOutput;
import com.thumbing.contentserver.service.ISearchService;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 10:53
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/search")
public class SearchController extends ThumbingBaseController {
    @Autowired
    ISearchService searchService;

    @ApiOperation("搜索文章和帖子")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(method = RequestMethod.GET)
    public List<SearchOutput> search(@Valid SearchInput input){
        return searchService.search(input, getCurrentUser());
    }
}
