package com.loserclub.shared.entity.mongo.record;

import com.loserclub.shared.entity.mongo.MongoCreationEntity;
import com.loserclub.shared.entity.mongo.common.NickUser;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:12
 */
//users{Set<(id+name)>}, last_message, last_time, is_read
@Document(collection = "chat_session")
@Data
public class ChatSession extends MongoCreationEntity {
    /**
     * 用户id
     */
    private Set<NickUser> users;
    /**
     * 最后一条消息的内容
     */
    private String lastMessage;
    /**
     * 最后一条消息的时间
     */
    private LocalDateTime lastTime;
    /**
     * 是否已读
     */
    private boolean read;
}
