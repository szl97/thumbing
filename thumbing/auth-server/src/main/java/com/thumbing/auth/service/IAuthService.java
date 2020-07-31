package com.thumbing.auth.service;

import com.thumbing.shared.entity.sql.system.User;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 9:46
 */
public interface IAuthService {
    /**
     * 校验接口是否有权限
     * @param applicationName
     * @param url
     * @return
     */
    boolean auth(String authorization , String applicationName,String url);


    User checkAndGetUser(String userName, String password);
}
