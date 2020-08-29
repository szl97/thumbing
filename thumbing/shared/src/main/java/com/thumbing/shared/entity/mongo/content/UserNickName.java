package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.entity.mongo.BaseMongoEntity;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/20 11:01
 */
@Document(collection = "user_nick_name")
@Data
@FieldNameConstants
public class UserNickName extends BaseMongoEntity {
    @Indexed
    private Long userId;
    private String nickName;
    @Indexed
    private String contentId;
    private ContentType contentType;
}
