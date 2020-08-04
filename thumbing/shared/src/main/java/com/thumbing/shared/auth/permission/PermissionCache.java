package com.thumbing.shared.auth.permission;

import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.condition.SecurityCondition;
import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtilsForCollection;
import com.thumbing.shared.utils.redis.RedisUtilsForObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 19:16
 */
@Conditional({
        RedisCondition.class,
        SecurityCondition.class
})
@Component
public class PermissionCache {
    private final String AUTH_PERMISSION_URL_KEY = CacheKeyConstants.AUTH_PERMISSION_URL_KEY;
    private final String AUTH_PERMISSION_ANONYMOUS_KEY = CacheKeyConstants.AUTH_PERMISSION_ANONYMOUS_KEY;
    @Autowired
    private RedisUtilsForCollection redisUtilsForCollection;
    @Autowired
    private RedisUtilsForObject redisUtilsForObject;

    @Data
    private class urlPermission implements Serializable {
        private String url;
        private String name;
    }

    public Set<String> getAnonymousPath(String applicationName){
        String key = getAnonymousKey(applicationName);
        return redisUtilsForCollection.getRedisUtilsForSet().members(key);
    }

    public void addAnonymousPath(String applicationName, List<String> path){
            String[] p = new String[path.size()];
            String key = getAnonymousKey(applicationName);
            redisUtilsForCollection.getRedisUtilsForSet().add(key, path.toArray(p));

    }

    private String getAnonymousKey(String applicationName){
        return  AUTH_PERMISSION_ANONYMOUS_KEY + applicationName;
    }

    public String getUrlPermission(String applicationName, String url){
        String key = getUrlKey(applicationName);
        return  redisUtilsForObject.getRedisUtilsForHash().get(key, url);
    }

    public void addUrlPermission(String applicationName, Map<String, String> urlPermissions){
        String key = getUrlKey(applicationName);
        redisUtilsForObject.getRedisUtilsForHash().put(key, urlPermissions);
    }

    private String getUrlKey(String applicationName){
        return  AUTH_PERMISSION_URL_KEY + applicationName;
    }
}
