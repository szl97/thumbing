package com.thumbing.contentserver.elasticsearch;

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
    private String abstracts;
}
