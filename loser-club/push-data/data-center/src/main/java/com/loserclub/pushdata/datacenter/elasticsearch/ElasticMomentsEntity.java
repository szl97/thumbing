package com.loserclub.pushdata.datacenter.elasticsearch;

import lombok.Data;

@Data
public class ElasticMomentsEntity extends ElasticBaseEntity {
    public ElasticMomentsEntity() {
        super();
        this.setName(Name.MOMENTS);
    }

    private String content;
}
