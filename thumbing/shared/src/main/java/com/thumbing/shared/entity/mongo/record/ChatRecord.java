package com.thumbing.shared.entity.mongo.record;

import com.thumbing.shared.entity.mongo.MongoCreationEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 11:06
 */
@Document(collection = "chat_record")
@Data
public class ChatRecord extends MongoCreationEntity {
    /**
     * 发送方Id
     */
    private Long fromId;
    /**
     * 用户Id
     */
    private Long toId;
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

    private Long userId1;
    private Long userId2;
}
