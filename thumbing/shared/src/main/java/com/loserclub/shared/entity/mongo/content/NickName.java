package com.loserclub.shared.entity.mongo.content;

import com.loserclub.shared.entity.mongo.BaseMongoEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 13:04
 */
@Document(collection = "nick_name")
@Data
public class NickName extends BaseMongoEntity {
    private String name;
    private int index;
}
