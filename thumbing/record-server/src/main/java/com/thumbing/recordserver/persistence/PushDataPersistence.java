package com.thumbing.recordserver.persistence;

import com.thumbing.shared.annotation.AccessLock;
import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import com.thumbing.shared.repository.mongo.record.IPushDataRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/17 10:18
 */
@Component
@Transactional
public class PushDataPersistence {
    @Autowired
    private IPushDataRecordRepository pushDataRecordRepository;

    @AccessLock(value = {"com.thumbing.shared.entity.mongo.record.PushDataRecord"},
            className = "com.thumbing.shared.entity.mongo.record.PushDataRecord",
            fields = {
                    "getPushType", "getDataId"
            })
    public PushDataRecord saveInDb(PushDataRecord pushDataRecord) {
        PushDataRecord record = null;
        if (pushDataRecord.getId() != null) {
            record = pushDataRecordRepository.findByDataIdAndPushType(pushDataRecord.getDataId(), pushDataRecord.getPushType()).orElse(null);
        }
        if (record != null) {
            if (pushDataRecord.isRead()) {
                pushDataRecord.setRead(true);
                return pushDataRecordRepository.save(pushDataRecord);
            }
            else {
                return record;
            }
        }
        else {
            return pushDataRecordRepository.save(pushDataRecord);
        }
    }
}
