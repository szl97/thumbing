package com.thumbing.shared.repository.mongo.record;

import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 16:17
 */
public interface IPushDataRecordRepository extends IBaseMongoRepository<PushDataRecord> {
    Optional<PushDataRecord> findByDataIdAndPushType(String dataId, PushDataTypeEnum type);
    List<PushDataRecord> findAllByToUserIdAndRead(Long id, boolean read, Sort sort);
    Page<PushDataRecord> findAllByToUserIdAndRead(Long id, boolean read, Pageable pageable);
}
