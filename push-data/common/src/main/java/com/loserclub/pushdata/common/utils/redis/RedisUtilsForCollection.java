package com.loserclub.pushdata.common.utils.redis;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * @author Stan Sai
 * @date 2020-06-29
 */

@Slf4j
@Data
public class RedisUtilsForCollection extends RedisUtils {

    RedisUtilsForList redisUtilForList;
    RedisUtilsForSet redisUtilsForSet;
    RedisUtilsForZSet redisUtilsForZSet;
    private static RedisUtilsForCollection INSTANCE = new RedisUtilsForCollection();
    private static Object lock = new Object();

    private RedisUtilsForCollection(){

    }

    /**
     * 多线程下的单例模式
     * 保证所持有的对象不会被初始化两次
     * @param redisTemplate
     * @return
     */
    public static RedisUtilsForCollection getInstance(RedisTemplate redisTemplate){
        if(INSTANCE.getRedisTemplate() == null){
            synchronized (lock){
                if(INSTANCE.getRedisTemplate() == null){
                    INSTANCE.setRedisTemplate(redisTemplate);
                    INSTANCE.setRedisUtilForList(RedisUtilsForList.getInstance(redisTemplate.opsForList()));
                    INSTANCE.setRedisUtilsForSet(RedisUtilsForSet.getInstance(redisTemplate.opsForSet()));
                    INSTANCE.setRedisUtilsForZSet(RedisUtilsForZSet.getInstance(redisTemplate.opsForZSet()));
                }
            }
        }
        return INSTANCE;
    }
}
