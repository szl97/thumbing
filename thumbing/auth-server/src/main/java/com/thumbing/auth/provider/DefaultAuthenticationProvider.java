package com.thumbing.auth.provider;

import com.thumbing.auth.service.IAuthService;
import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.entity.sql.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 10:11
 */
@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    IAuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        //取出传递的 账户密码上下文
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        User user = authService.checkAndGetUser(username, password);
        UserContext context = new UserContext();
        //写入数据
        context.setId(user.getId());
        context.setName(user.getUserName());
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user.isAccess()){
            authorities.add(new SimpleGrantedAuthority(PermissionConstants.ACCESS));
        }
        if(user.isActive()){
            authorities.add(new SimpleGrantedAuthority(PermissionConstants.REGISTER));
        }
        context.setAuthorities(authorities);
        return new UsernamePasswordAuthenticationToken(context, null, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
