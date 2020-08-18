package com.thumbing.contentserver.elasticsearch;

import lombok.Data;

/**
 * @author Stan Sai
 * @date 2020-07-03
 */
@Data
public class ElasticMomentsEntity extends ElasticBaseEntity {
    public ElasticMomentsEntity() {
        super();
        this.setName(Name.MOMENTS);
    }
    private String content;
}
