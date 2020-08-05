package com.thumbing.auth.service.impl;


import com.github.dozermapper.core.Mapper;
import com.thumbing.auth.cache.ValidationCache;
import com.thumbing.auth.dto.input.ChangePasswordRequest;
import com.thumbing.auth.dto.input.SignUpInput;
import com.thumbing.auth.dto.input.CheckUniqueInput;
import com.thumbing.auth.dto.output.AccountDto;
import com.thumbing.auth.service.IAccountService;
import com.thumbing.shared.entity.sql.system.Device;
import com.thumbing.shared.entity.sql.system.User;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.repository.sql.system.IUserRepository;
import com.thumbing.shared.service.impl.BaseSqlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 16:59
 */
@Service
@Transactional
public class AccountService extends BaseSqlService<User, IUserRepository> implements IAccountService {
    @Autowired
    private ValidationCache validationCache;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Mapper mapper;

    @Override
    public AccountDto register(SignUpInput signUpInput) {
        if(StringUtils.isAllBlank(signUpInput.getPhoneNUm(), signUpInput.getEmail())){
            throw new BusinessException("需要手机号或邮箱");
        }
        String key = StringUtils.isNotBlank(signUpInput.getPhoneNUm()) ? signUpInput.getPhoneNUm() : signUpInput.getEmail();
        String validationCode = validationCache.findRegisterCode(key);
        if(validationCode == null){
            throw new BusinessException("验证码已失效或验证码未发送");
        }
        if(validationCode != signUpInput.getValidation()){
            throw new BusinessException("验证码错误");
        }
        User user = mapper.map(signUpInput, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(signUpInput.getDeviceInput() != null) {
            Device device = mapper.map(signUpInput.getDeviceInput(), Device.class);
            user.getDevices().add(device);
        }
        user.setActive(true);
        user.setAccess(true);
        if(StringUtils.isBlank(user.getEmail())){
            user.setEmail("000");
        }else if(StringUtils.isBlank(user.getPhoneNum())){
            user.setPhoneNum("000");
        }
        User entity = repository.save(user);
        return mapper.map(entity, AccountDto.class);
    }

    @Override
    public AccountDto changePassword(ChangePasswordRequest changePasswordRequest) {
        if(StringUtils.isAllBlank(changePasswordRequest.getOldPassWord(), changePasswordRequest.getOldPassWord())){
            throw new BusinessException("请输入原密码或验证码");
        }
        User user;
        if(StringUtils.isNotBlank(changePasswordRequest.getValidation())){
            String validationCode = validationCache.findChangerCode(changePasswordRequest.getUserName());
            if(validationCode == null){
                throw new BusinessException("验证码已失效或验证码未发送");
            }
            if(validationCode != changePasswordRequest.getValidation()){
                throw new BusinessException("验证码错误");
            }
            user = repository.findByUserName(changePasswordRequest.getUserName()).orElseThrow(()->new BusinessException("未知错误"));
        } else {
            user = repository.findByUserName(changePasswordRequest.getUserName()).orElseThrow(()->new BusinessException("未知错误"));
            if(!passwordEncoder.matches(changePasswordRequest.getPassword(), user.getPassword())) throw new BusinessException("原密码错误");
            if(changePasswordRequest.getOldPassWord().equals(changePasswordRequest.getPassword())) throw new BusinessException("新密码不可和原密码相同");
        }
        user.setPassword(changePasswordRequest.getPassword());
        user = repository.save(user);
        return mapper.map(user, AccountDto.class);
    }

    @Override
    public Boolean checkUnique(CheckUniqueInput checkUniqueInput) {
        User user = null;
        switch (checkUniqueInput.getType()){
            case Email: {
                user = repository.findByEmail(checkUniqueInput.getData()).orElse(null);
                break;
            }
            case Phone:{
                user = repository.findByPhoneNum(checkUniqueInput.getData()).orElse(null);
                break;
            }
            case UserName:{
                user = repository.findByUserName(checkUniqueInput.getData()).orElse(null);
                break;
            }
        }
        return null == user;
    }

    @Override
    public Boolean simulateRegisterSms(String phoneNum) {
        validationCache.storeRegisterCode(phoneNum, "1234");
        return true;
    }

    @Override
    public Boolean simulateChangePasswordSms(String phoneNum) {
        validationCache.storeChangerCode(phoneNum, "1234");
        return true;
    }
}
