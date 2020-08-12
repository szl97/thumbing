package com.thumbing.shared.repository.mongo.record;

import com.thumbing.shared.entity.mongo.record.ChatSession;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/19 11:24
 */
public interface IChatSessionRepository extends IBaseMongoRepository<ChatSession> {
    Optional<ChatSession> findByUserId1AndUserIs2(Long id1, Long id2);
}
