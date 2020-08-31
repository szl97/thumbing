package com.thumbing.contentserver.config;

import com.thumbing.shared.constants.CacheKeyConstants;
import com.thumbing.shared.utils.redis.RedisUtilsForList;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/31 8:28
 */
@Configuration
public class NickNameConfig {
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;
    private final String nickNameList = CacheKeyConstants.NICK_NAME_LIST;
    @PostConstruct
    public void initialize(){
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 10000; i++){
            list.add("用户"+i);
        }
        RedisUtilsForList.leftPush(redisTemplate.opsForList(), nickNameList, list);
    }
}
