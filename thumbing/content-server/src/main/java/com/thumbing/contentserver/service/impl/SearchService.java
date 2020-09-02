package com.thumbing.contentserver.service.impl;

import com.github.dozermapper.core.Mapper;
import com.thumbing.contentserver.config.ElasticSearchConfig;
import com.thumbing.contentserver.dto.input.SearchInput;
import com.thumbing.contentserver.dto.output.SearchOutput;
import com.thumbing.contentserver.elasticsearch.ElasticBaseEntity;
import com.thumbing.contentserver.elasticsearch.ElasticUtils;
import com.thumbing.contentserver.service.ISearchService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.utils.dozermapper.DozerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 10:29
 */
@Service
@Transactional
public class SearchService implements ISearchService {
    @Autowired
    private ElasticUtils elasticUtils;
    @Autowired
    private Mapper mapper;

    @Override
    public List<SearchOutput> search(SearchInput input, UserContext userContext) {
        List<ElasticBaseEntity> entities = elasticUtils.searchDocs(ElasticSearchConfig.indexName,
                input.getKeyword(),
                ElasticSearchConfig.searchFields,
                input.getPageNumber(),
                input.getPageSize(),
                ElasticBaseEntity.class);
        List<SearchOutput> result = DozerUtils.mapList(mapper, entities, SearchOutput.class, (s,t)->{
            t.setContentType(s.getName());
            if(StringUtils.isNotBlank(s.getTags())){
                t.setTagIds(Arrays.asList(s.getTags().split(",")));
            }
            t.setCreateTime(s.getDateTime());
        });
        return result;
    }
}
