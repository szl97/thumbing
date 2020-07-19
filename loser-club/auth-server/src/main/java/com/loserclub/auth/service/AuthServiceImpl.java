package com.loserclub.auth.service;

import com.loserclub.shared.auth.model.UserContext;
import com.loserclub.shared.cache.PermissionCache;
import com.loserclub.shared.auth.permission.SkipPathRequestMatcher;
import com.loserclub.shared.entity.sql.system.User;
import com.loserclub.shared.exception.BusinessException;
import com.loserclub.shared.exception.UserContextException;
import com.loserclub.shared.jwt.JwtTokenFactory;
import com.loserclub.shared.jwt.extractor.JwtHeaderTokenExtractor;
import com.loserclub.shared.repository.sql.system.IUserRepository;
import com.loserclub.shared.service.IAuthService;
import com.loserclub.shared.utils.user.UserContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 9:52
 */
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {
    @Autowired
    JwtTokenFactory jwtTokenFactory;
    @Autowired
    JwtHeaderTokenExtractor jwtHeaderTokenExtractor;
    @Autowired
    UserContextUtils userContextUtils;
    @Autowired
    SkipPathRequestMatcher skipPathRequestMatcher;
    @Autowired
    PermissionCache permissionCache;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRepository repository;

    @Override
    public boolean auth(String authorization, String applicationName, String url) {
        if(ignoreAuthentication(applicationName, url))  return true;
        return hasPermission(authorization, applicationName, url);
    }

    @Override
    public User checkAndGetUser(String userName, String password) {
        User user = repository.findByUserName(userName).orElseThrow(()-> new UsernameNotFoundException("用户名不存在"));
        if(!passwordEncoder.matches(password, user.getPassword())) throw new BadCredentialsException("密码错误");
        if(!user.isActive()) throw new BusinessException("未绑定手机或邮箱");
        return user;
    }

    /**
     * 是否跳过权限验证
     *
     * @param url
     * @return
     */
    private boolean ignoreAuthentication(String applicationName, String url) {
        return skipPathRequestMatcher.matches(applicationName, url);
    }

    /**
     * 是否有访问权限
     * @param authorization
     * @param applicationName
     * @param url
     * @return
     */
    private boolean hasPermission(String authorization, String applicationName, String url) {
        UserContext userContext = userContextUtils.getUserContext(authorization);
        if (userContext == null) {
            //TODO:抛出gateway可识别得异常
            throw new UserContextException("尚未登陆");
        }
        String urlPermission = permissionCache.getUrlPermission(applicationName, url);
        GrantedAuthority needAuthority = new SimpleGrantedAuthority(urlPermission);
        List<GrantedAuthority> authorities = userContext.getAuthorities();
        return authorities.contains(needAuthority);
    }
}
