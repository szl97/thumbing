package com.thumbing.contentserver.service;

import com.thumbing.contentserver.dto.input.SearchInput;
import com.thumbing.contentserver.dto.output.SearchOutput;
import com.thumbing.shared.auth.model.UserContext;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 10:12
 */
public interface ISearchService {
    /**
     * 搜索文章或帖子
     * @param input
     * @param userContext
     * @return
     */
    List<SearchOutput> search(SearchInput input, UserContext userContext);
}
