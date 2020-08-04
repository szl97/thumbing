package com.thumbing.shared.auth.config;

import com.thumbing.shared.annotation.AllowAnonymous;
import com.thumbing.shared.auth.permission.CustomAuthProvider;
import com.thumbing.shared.auth.permission.PermissionManager;
import com.thumbing.shared.condition.RedisCondition;
import com.thumbing.shared.condition.SecurityCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 20:18
 */
@Conditional({
        RedisCondition.class,
        SecurityCondition.class
        })
@Component
public class AuthConfig {
    @Value("${security.ignores}")
    private String ignores;
    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    PermissionManager permissionManager;
    @Autowired
    RequestMappingHandlerMapping mapping;
    @Autowired
    CustomAuthProvider authProvider;
    @PostConstruct
    public void  init(){
        //获取跳过权限验证得清单
        List<String> allowAnonymousPath=new ArrayList<>();
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> item : map.entrySet()) {
            PatternsRequestCondition p = item.getKey().getPatternsCondition();
            AllowAnonymous annotation = item.getValue().getMethod().getAnnotation(AllowAnonymous.class);
            if(annotation==null){
                continue;
            }
            for (String url : p.getPatterns()) {
                if(!allowAnonymousPath.contains(url)){
                    allowAnonymousPath.add(url);
                }
            }
        }
        if(StringUtils.isNotBlank(ignores)) {
            allowAnonymousPath.addAll(Arrays.asList(ignores.split(";")));
        }

        authProvider.provideAuthInfo(applicationName,allowAnonymousPath, permissionManager.getUrlPermission());
    }
}
