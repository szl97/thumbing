package com.thumbing.shared.repository.mongo.content;

import com.thumbing.shared.entity.mongo.content.Moments;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/19 11:21
 */
public interface IMomentsRepository extends IBaseMongoRepository<Moments> {
    Optional<Moments> findByIdAndIsDelete(String id, int isDelete);

    Page<Moments> findAllByIsDelete(int isDelete, Pageable pageable);

    Page<Moments> findAllByUserIdAndIsDelete(Long userId, int isDelete, Pageable pageable);
}
