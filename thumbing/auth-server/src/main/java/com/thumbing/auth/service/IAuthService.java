package com.thumbing.auth.service;

import com.thumbing.auth.dto.input.LoginRequest;
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

    boolean auth(String userName);
    /**
     * 检查用户名和密码是否正确
     * @param userName
     * @param password
     * @return
     */
    User checkAndGetUser(String userName, String password);

    /**
     * 登录成功的处理
     * @param loginRequest
     */
    void succeedLogin(LoginRequest loginRequest);

    /**
     * 登录失败的处理
     * @param loginRequest
     */
    void failLogin(LoginRequest loginRequest);
}
