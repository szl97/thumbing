package com.thumbing.shared.auth.permission;

import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.condition.SecurityCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 18:12
 */
@Conditional({
        RedisCondition.class,
        SecurityCondition.class
})
@Component
public class PermissionManager {
    @Autowired
    private RequestMappingHandlerMapping mapping;
    public Map<String, String> getUrlPermission(){ return urlPermission; }
    private final Map<String, String> urlPermission;
    public PermissionManager() {
        urlPermission = new HashMap<>();
    }
    @PostConstruct
    public void initialize() {
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> item : map.entrySet()) {
            PatternsRequestCondition p = item.getKey().getPatternsCondition();
            Authorize annotation = item.getValue().getMethod().getAnnotation(Authorize.class);
            if (annotation == null) {
                continue;
            }
            for (String url : p.getPatterns()){
                String permission = annotation.value();
                urlPermission.put(url, permission);
            }
        }
    }
}
