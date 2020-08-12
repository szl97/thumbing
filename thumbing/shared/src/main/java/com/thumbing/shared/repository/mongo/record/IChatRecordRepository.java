package com.thumbing.shared.repository.mongo.record;

import com.thumbing.shared.entity.mongo.record.ChatRecord;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/19 11:23
 */
public interface IChatRecordRepository extends IBaseMongoRepository<ChatRecord> {
    Optional<ChatRecord> findByUserId1AndUserIs2(Long id1, Long id2);
}
