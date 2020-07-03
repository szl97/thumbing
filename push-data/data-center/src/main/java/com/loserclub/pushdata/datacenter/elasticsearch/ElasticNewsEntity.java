package com.loserclub.pushdata.datacenter.elasticsearch;

import lombok.Data;

@Data
public class ElasticNewsEntity extends ElasticBaseEntity {
    public ElasticNewsEntity(){
        super();
        this.setName(Name.NEWS);
    }
    private String content;
}
