package com.thumbing.recordserver.persistence;

import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.entity.mongo.record.ChatRecord;
import com.thumbing.shared.repository.mongo.record.IChatRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 14:49
 */
@Component
@Transactional
public class RecordPersistence {
    @Autowired
    private IChatRecordRepository chatRepository;

    @AccessLock(value = {"com.thumbing.shared.entity.mongo.record.ChatRecord"},
            className = "com.thumbing.shared.entity.mongo.record.ChatRecord",
            fields = {
                    "getDataId"
            })
    public ChatRecord saveInDb(ChatRecord chatRecord) {
        ChatRecord record = null;
        if (chatRecord.getId() != null) {
            record = chatRepository.findByDataId(chatRecord.getDataId()).orElse(null);
        }
        if (record != null) {
            if(chatRecord.isCancel() || chatRecord.isRead()) {
                if (chatRecord.isRead()) {
                    chatRecord.setRead(true);
                    chatRecord.setReadTime(record.getReadTime());
                }
                if (chatRecord.isCancel()) {
                    chatRecord.setCancel(true);
                }
                return chatRepository.save(chatRecord);
            }
            else {
                return record;
            }
        }
        else {
            return chatRepository.save(chatRecord);
        }
    }
}
