package com.thumbing.shared.entity.mongo.record;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.utils.serializer.PushTypeDeserializer;
import com.thumbing.shared.utils.serializer.PushTypeSerializer;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 16:15
 */
@Document(collection = "push_data_record")
@Data
public class PushDataRecord extends MongoCreationEntity {
    private Long toUserId;
    private Long fromUserId;
    private String fromUserName;
    private String fromUserNickName;
    private String data;
    @JsonSerialize(using = PushTypeSerializer.class)
    @JsonDeserialize(using = PushTypeDeserializer.class)
    private PushDataTypeEnum pushType;
    private boolean allUser;
}
