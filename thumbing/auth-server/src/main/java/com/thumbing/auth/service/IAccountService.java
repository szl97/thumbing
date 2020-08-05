package com.thumbing.auth.service;

import com.thumbing.auth.dto.input.ChangePasswordRequest;
import com.thumbing.auth.dto.input.SignUpInput;
import com.thumbing.auth.dto.input.CheckUniqueInput;
import com.thumbing.auth.dto.output.AccountDto;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 9:01
 */
public interface IAccountService {
    /**
     * 新用户注册接口
     * @param signUpInput
     * @return
     */
    AccountDto register(SignUpInput signUpInput);
    /**
     * 修改密码
     * @param changePasswordRequest
     * @return
     */
    AccountDto changePassword(ChangePasswordRequest changePasswordRequest);
    /**
     * 验证手机、邮箱、用户名
     * @param checkUniqueInput
     * @return
     */
    Boolean checkUnique(CheckUniqueInput checkUniqueInput);

    Boolean simulateRegisterSms(String phoneNum);
    Boolean simulateChangePasswordSms(String phoneNum)
}
