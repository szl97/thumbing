package com.thumbing.shared.repository.sql.personal;

import com.thumbing.shared.entity.sql.personal.Personal;
import com.thumbing.shared.repository.sql.IBaseSqlRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;

import java.awt.print.Pageable;
import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/19 11:19
 */
public interface IPersonalRepository extends IBaseSqlRepository<Personal> {
    @Override
    @EntityGraph(Personal.NamedEntityGraph_userInfo)
    Optional<Personal> findById(Long id);

    @EntityGraph(Personal.NamedEntityGraph_userInfo)
    Optional<Personal> findByUserId(Long id);

}
