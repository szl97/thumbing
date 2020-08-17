package com.thumbing.recordserver.handler;

import com.github.dozermapper.core.Mapper;
import com.thumbing.recordserver.persistence.PushDataPersistence;
import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.message.RelationApplyMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 16:18
 */
@Component
public class PushDataHandler {
    @Autowired
    PushDataPersistence pushDataPersistence;
    @Autowired
    Mapper mapper;

    public void handle(RelationApplyMsg msg){
        PushDataRecord dataRecord = mapper.map(msg, PushDataRecord.class);
        dataRecord.setDataId(msg.getDataId());
        dataRecord.setAllUser(false);
        dataRecord.setRead(false);
        dataRecord.setData(msg.getRemark());
        dataRecord.setPushType(PushDataTypeEnum.RA);
        dataRecord.setCreateTime(msg.getTime());
        pushDataPersistence.saveInDb(dataRecord);
    }
}
