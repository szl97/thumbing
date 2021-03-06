package com.thumbing.shared.repository.sql;

import com.thumbing.shared.entity.sql.BaseSqlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */
public interface IBaseSqlRepository<T extends BaseSqlEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
