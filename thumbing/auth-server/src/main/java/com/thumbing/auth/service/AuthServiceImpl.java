package com.thumbing.auth.service;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.cache.PermissionCache;
import com.thumbing.shared.auth.permission.SkipPathRequestMatcher;
import com.thumbing.shared.dto.PagedAndSortedInput;
import com.thumbing.shared.entity.sql.system.User;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.exception.UserContextException;
import com.thumbing.shared.jpa.Specifications;
import com.thumbing.shared.jwt.JwtTokenFactory;
import com.thumbing.shared.jwt.extractor.JwtHeaderTokenExtractor;
import com.thumbing.shared.repository.sql.system.IUserRepository;
import com.thumbing.shared.utils.user.UserContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
