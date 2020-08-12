package com.thumbing.shared.repository.sql.user;

import com.thumbing.shared.entity.sql.user.UserInfo;
import com.thumbing.shared.repository.sql.IBaseSqlRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 10:12
 */
public interface IUserInfoRepository extends IBaseSqlRepository<UserInfo> {
    @EntityGraph(UserInfo.personalNamedGraph)
    Optional<UserInfo> findByUserId(Long id);
}
