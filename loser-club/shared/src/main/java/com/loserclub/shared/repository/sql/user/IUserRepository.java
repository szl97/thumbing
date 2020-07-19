package com.loserclub.shared.repository.sql.user;

import com.loserclub.shared.entity.sql.system.User;
import com.loserclub.shared.repository.sql.IBaseSqlRepository;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 14:56
 */
public interface IUserRepository extends IBaseSqlRepository<User> {
    Optional<User> findByUserName(String userName);
}
