package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/20 11:01
 */
@Document(collection = "user_nick_name")
@Data
@FieldNameConstants
public class UserNickName extends BaseMongoEntity {
    private Long userId;
    private String nickName;
}
