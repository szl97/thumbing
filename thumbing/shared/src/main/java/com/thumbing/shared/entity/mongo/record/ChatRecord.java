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
     * 对应的用户会话的id
     */
    private long chatSessionId;
    /**
     * 发送方Id
     */
    private long fromId;
    /**
     * 用户Id
     */
    private long toId;
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

}
