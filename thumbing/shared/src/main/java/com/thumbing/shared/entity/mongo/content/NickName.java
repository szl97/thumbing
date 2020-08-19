package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 13:04
 */
@Document(collection = "nick_name")
@Data
@FieldNameConstants
public class NickName extends BaseMongoEntity {
    private String name;
    private int sequence;
}
