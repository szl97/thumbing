package com.thumbing.shared.auth.permission;

import com.thumbing.shared.cache.PermissionCache;
import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.condition.SecurityCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 20:08
 */
@Conditional({
        RedisCondition.class,
        SecurityCondition.class
})
@Component
public class CustomAuthProvider {
    @Autowired
    PermissionCache permissionCache;
    public void provideAuthInfo(String applicationName, List<String> allowAnonymousPath, Map<String, String> urlPermissions) {
        permissionCache.addUrlPermission(applicationName, urlPermissions);
        permissionCache.addAnonymousPath(applicationName,allowAnonymousPath);
    }
}
