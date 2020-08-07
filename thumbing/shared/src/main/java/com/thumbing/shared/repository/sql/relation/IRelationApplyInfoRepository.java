package com.thumbing.shared.repository.sql.relation;

import com.thumbing.shared.entity.sql.relation.RelationApplyInfo;
import com.thumbing.shared.repository.sql.IBaseSqlRepository;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 16:36
 */
public interface IRelationApplyInfoRepository extends IBaseSqlRepository<RelationApplyInfo> {
    Optional<RelationApplyInfo> findByUserIdAndTargetUserId(Long id, Long targetId);
}
