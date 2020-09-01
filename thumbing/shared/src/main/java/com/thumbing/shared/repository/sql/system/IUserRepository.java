package com.thumbing.shared.repository.sql.system;

import com.thumbing.shared.entity.sql.system.User;
import com.thumbing.shared.repository.sql.IBaseSqlRepository;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 14:56
 */
public interface IUserRepository extends IBaseSqlRepository<User> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNum(String phoneNum);
}
