package com.thumbing.shared.entity.mongo.record;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.utils.serializer.PushTypeDeserializer;
import com.thumbing.shared.utils.serializer.PushTypeSerializer;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 16:15
 */
@Document(collection = "push_data_record")
@Data
@FieldNameConstants
public class PushDataRecord extends MongoCreationEntity {
    /**
     * 消息ID
     */
    private String dataId;
    /**
     * 接受方iD
     */
    private Long toUserId;
    /**
     * 发送方Id
     */
    private Long fromUserId;
    /**
     * 发送方用户名
     */
    private String fromUserName;
    /**
     * 发送方昵称
     */
    private String fromUserNickName;
    /**
     * 推送内容
     */
    private String data;
    /**
     * 推送类型
     */
    @JsonSerialize(using = PushTypeSerializer.class)
    @JsonDeserialize(using = PushTypeDeserializer.class)
    private PushDataTypeEnum pushType;
    /**
     * 是否已读
     */
    private boolean read;
    /**
     * 已读时间
     */
    private LocalDateTime readTime;
}
