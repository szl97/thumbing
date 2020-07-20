package com.loserclub.auth.service;

import com.loserclub.shared.auth.model.UserContext;
import com.loserclub.shared.entity.sql.system.User;

import java.util.List;
import java.util.Map;

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
