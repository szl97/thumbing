package com.loserclub.pushdata.datacenter.elasticsearch;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ElasticBaseEntity {
    private final String index = "doc";
    private String id;
    private Name name;
    private String tags;
    private LocalDateTime dateTime;

    protected enum Name{
        ARTICLE("article"),
        NEWS("news");
        private String value;
        Name(String value){
            this.value = value;
        }
        public String value(){
            return this.value;
        }
    }
}
