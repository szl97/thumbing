package com.loserclub.shared.entity.mongo.record;

import com.loserclub.shared.entity.mongo.MongoCreationEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 11:06
 */
//is_group, from_id, to_id, content, is_cancel,chat_session_id
@Document(collection = "chat_record")
@Data
public class ChatRecord extends MongoCreationEntity {
    /**
     * 对应的用户会话的id
     */
    private long chat_session_id;
    /**
     * 是否是群聊消息
     */
    private boolean group;
    /**
     * 发送方Id
     */
    private long from_id;
    /**
     * 如果是群聊就是群Id，否则就是用户Id
     */
    private long to_id;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否撤回
     */
    private boolean cancel;

}
