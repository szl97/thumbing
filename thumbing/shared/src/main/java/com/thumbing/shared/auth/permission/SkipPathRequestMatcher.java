package com.thumbing.shared.auth.permission;

import com.thumbing.shared.cache.PermissionCache;
import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.condition.SecurityCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;

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
    PermissionCache permissionCache;

    public boolean matches(String applicationName, String url){
        List<String> anonymousPath = permissionCache.getAnonymousPath(applicationName);
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
