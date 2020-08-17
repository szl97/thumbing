package com.thumbing.shared.entity.mongo.record;

import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 11:06
 */
@Document(collection = "chat_record")
@Data
@FieldNameConstants
public class ChatRecord extends MongoCreationEntity {
    private Long dataId;
    /**
     * 发送方Id
     */
    private Long fromId;
    /**
     * 用户Id
     */
    private Long toId;
    private String fromUserName;
    private String toUserName;
    private String fromNickName;
    private String toNickName;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否撤回
     */
    private boolean cancel;
    /**
     * 接收方是否已读
     */
    private boolean read;
    /**
     * 已读时间
     */
    private LocalDateTime readTime;
    private Long userId1;
    private Long userId2;
}
