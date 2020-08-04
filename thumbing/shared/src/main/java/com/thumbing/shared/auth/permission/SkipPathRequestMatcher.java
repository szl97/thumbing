package com.thumbing.shared.auth.permission;

import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.condition.SecurityCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 20:39
 */
@Conditional({
        RedisCondition.class,
        SecurityCondition.class
})
@Component
public class SkipPathRequestMatcher {
    @Autowired
    private PermissionCache permissionCache;

    private final String LOGIN = "/login";
    private final String LOGIN_APP = "auth-server";

    public boolean matches(String applicationName, String url){
        if(url.equals(LOGIN) && applicationName.equals(LOGIN_APP)){
            return true;
        }
        Set<String> anonymousPath = permissionCache.getAnonymousPath(applicationName);
        AntPathMatcher pathMatcher=new AntPathMatcher();
        if(anonymousPath != null && anonymousPath.size()>0){
            for(String path:anonymousPath){
                if(pathMatcher.match(path,url)){
                    return true;
                }
            }
        }
        return false;
    }
}
