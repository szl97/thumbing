package com.loserclub.shared.entity.mongo.record;

import com.loserclub.shared.entity.mongo.MongoCreationEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:12
 */
//user_id(unique index), chat_record_ids
@Document(collection = "user_record")
@Data
public class UserRecord extends MongoCreationEntity {
    /**
     * 用户id
     */
    private long userId;
    /**
     * 用户的所有的聊天记录
     */
    private Set<Long> chat_record_ids;
}
