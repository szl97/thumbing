package com.thumbing.shared.repository.mongo.content;

import com.thumbing.shared.entity.mongo.content.Roast;
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
 * @Date: 2020/7/19 11:22
 */
public interface IRoastRepository extends IBaseMongoRepository<Roast> {
    Optional<Roast> findByIdAndIsDelete(String id, int isDelete);

    Page<Roast> findAllByIsDelete(int isDelete, Pageable pageable);

    Page<Roast> findAllByUserIdAndIsDelete(Long userId, int isDelete, Pageable pageable);
}
