package com.thumbing.shared.repository.mongo.record;

import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 16:17
 */
public interface IPushDataRecordRepository extends IBaseMongoRepository<PushDataRecord> {
    Optional<PushDataRecord> findByDataIdAndPushType(String dataId, PushDataTypeEnum type);
}
