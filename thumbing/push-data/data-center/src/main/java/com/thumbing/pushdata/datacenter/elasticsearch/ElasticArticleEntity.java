package com.thumbing.pushdata.datacenter.elasticsearch;

import lombok.Data;

/**
 * @author Stan Sai
 * @date 2020-07-03
 */
@Data
public class ElasticArticleEntity extends ElasticBaseEntity {
    public ElasticArticleEntity() {
        super();
        this.setName(Name.ARTICLE);
    }

    private String title;
}
