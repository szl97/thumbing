package com.loserclub.pushdata.datacenter.elasticsearch;

import lombok.Data;

@Data
public class ElasticArticleEntity extends ElasticBaseEntity {
    public ElasticArticleEntity(){
        super();
        this.setName(Name.ARTICLE);
    }
    private String title;
}
