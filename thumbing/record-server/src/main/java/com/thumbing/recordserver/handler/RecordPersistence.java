package com.thumbing.recordserver.handler;

import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.entity.mongo.record.ChatRecord;
import com.thumbing.shared.repository.mongo.record.IChatRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 14:49
 */
@Component
public class RecordPersistence {
    @Autowired
    IChatRecordRepository chatRepository;

    @AccessLock(value = "com.thumbing.shared.entity.mongo.record.ChatRecord",
            className = "com.thumbing.shared.entity.mongo.record.ChatRecord",
            fields = {
                    "getUserId1", "getUserId2", "getCreateTime"
            })
    public ChatRecord saveInDb(ChatRecord chatRecord) {
        ChatRecord record = null;
        if (chatRecord.getId() != null) {
            record = chatRepository.findById(chatRecord.getId()).orElse(null);
        }
        if (record != null) {

            if (chatRecord.isRead()) {
                chatRecord.setRead(true);
            }
            if (chatRecord.isCancel()) {
                chatRecord.setCancel(true);
            }
        }
        return chatRepository.save(chatRecord);
    }
}
