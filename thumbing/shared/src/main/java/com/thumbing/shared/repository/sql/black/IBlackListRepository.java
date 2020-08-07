package com.thumbing.shared.repository.sql.black;

import com.thumbing.shared.entity.sql.black.BlackList;
import com.thumbing.shared.repository.sql.IBaseSqlRepository;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 16:41
 */
public interface IBlackListRepository extends IBaseSqlRepository<BlackList> {
    Optional<BlackList> findByUserIdAndTargetUserId(Long id1, Long id2);
}
