package com.thumbing.shared.repository.sql.relation;

import com.thumbing.shared.entity.sql.relation.Relation;
import com.thumbing.shared.repository.sql.IBaseSqlRepository;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/19 11:16
 */
public interface IRelationRepository extends IBaseSqlRepository<Relation> {
    Optional<Relation> findByUserIdOneOrUserIdTwo(Long idOne, Long idTwo);

    Optional<Relation> findByUserIdOneAndUserIdTwo(Long idOne, Long idTwo);
}
