package com.thumbing.shared.repository.mongo.record;

import com.thumbing.shared.entity.mongo.record.ChatRecord;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/19 11:23
 */
public interface IChatRecordRepository extends IBaseMongoRepository<ChatRecord> {

    Optional<ChatRecord> findByDataId(Long id);

    Page<ChatRecord> findByUserId1AndUserId2AndCreateTimeIsAfter(Long id1, Long id2, LocalDateTime time, Pageable pageable);

    List<ChatRecord> findByToIdAndRead(Long id1, boolean read, Sort sort);
}
