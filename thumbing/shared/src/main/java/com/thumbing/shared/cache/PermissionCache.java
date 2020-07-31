package com.thumbing.shared.cache;

import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.condition.SecurityCondition;
import com.thumbing.shared.utils.redis.RedisUtilsForCollection;
import com.thumbing.shared.utils.redis.RedisUtilsForObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private final String AUTH_SET_PERMISSION_URL_KEY = "AUTH:SET:PERMISSION:URL:APP:";
    private final String AUTH_PERMISSION_URL_KEY = "AUTH:PERMISSION:URL:APP:";
    private final String AUTH_PERMISSION_ANONYMOUS_KEY = "AUTH:PERMISSION:ANONYMOUS:APP:";
    @Autowired
    RedisUtilsForCollection redisUtilsForCollection;
    @Autowired
    RedisUtilsForObject redisUtilsForObject;

    @Data
    private class urlPermission implements Serializable {
        private String url;
        private String name;
    }

    public List<String> getAnonymousPath(String applicationName){
        String key = getAnonymousKey(applicationName);
        return redisUtilsForCollection.getRedisUtilForList().getAll(key);
    }

    public void addAnonymousPath(String applicationName, List<String> path){
        String setKey = AUTH_SET_PERMISSION_URL_KEY + applicationName;
        List<String> newPath = new ArrayList<>();
        for(String p : path) {
            if (!redisUtilsForCollection.getRedisUtilsForSet().isExist(setKey, p)){
                redisUtilsForCollection.getRedisUtilsForSet().add(setKey, p);
                newPath.add(p);
            }
        }
        if(newPath.size() > 0) {
            String key = getAnonymousKey(applicationName);
            redisUtilsForCollection.getRedisUtilForList().rightPush(key, newPath);
        }
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
