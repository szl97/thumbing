package com.thumbing.shared.utils.redis;

import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-06-29
 */
@Slf4j
@Data
@UtilityClass
public class RedisUtilsForHash {

    /**
     * 根据 key 获取散列中对应hash key的value 如果key不存在则返回null
     *
     * @param key
     * @param hashKey
     * @return
     */
    public <T> T getObject(HashOperations<String, String, T> hashOperations, String key, String hashKey) {
        return hashOperations.get(key, hashKey);
    }


    /**
     * 根据 key 获取散列中对应hash key的指定类型的value 如果key不存在则返回null
     *
     * @param key
     * @param hashKey
     * @return
     */
    public <T> T get(HashOperations<String, String, T> hashOperations, String key, String hashKey) {
        T val = hashOperations.get(key, hashKey);
        return  val;
    }


    /**
     * 根据 key 按hash key集合的顺序获取value 如果key不存在则返回null
     *
     * @param key
     * @param hashKeys
     * @return
     */
    public <T> List<T> get(HashOperations<String, String, T> hashOperations, String key, List<String> hashKeys) {
        List<T> val = hashOperations.multiGet(key, hashKeys);
        return val;
    }

    /**
     * 获取散列中所有的hash key集合
     *
     * @param key
     * @return
     */
    public <T> Set<String> getAllKeys(HashOperations<String, String, T> hashOperations, String key) {
        return hashOperations.keys(key);
    }

    /**
     * 获取散列中所有的value
     *
     * @param key
     * @return
     */
    public <T> List<T> getAllValues(HashOperations<String, String, T> hashOperations, String key) {
        return hashOperations.values(key);
    }

    /**
     * 获取散列中所有的键值对
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getAllEntries(HashOperations<String, String, T> hashOperations, String key) {
        return hashOperations.entries(key);
    }


    /**
     * 获取hash的大小
     *
     * @param key
     * @return
     */
    public <T> Long size(HashOperations<String, String, T> hashOperations, String key) {
        return hashOperations.size(key);
    }

    ;


    /**
     * 为散列添加或者覆盖一个 key-value键值对
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public <T> void put(HashOperations<String, String, T> hashOperations, String key, String hashKey, T value) {
        hashOperations.put(key, hashKey, value);
    }

    /**
     * 为散列添加多个key-value键值对
     *
     * @param key
     * @param map
     */
    public <T> void put(HashOperations<String, String, T> hashOperations, String key, Map<String, T> map) {
        hashOperations.putAll(key, map);
    }


    /**
     * 为散列添加一个key-value键值对。如果存在则不添加不覆盖。返回false
     * 设置成功返回true 失败返回false
     *
     * @param key
     * @param value
     * @return
     */
    public <T> Boolean setNx(HashOperations<String, String, T> hashOperations, String key, String hashKey, T value) {
        return hashOperations.putIfAbsent(key, hashKey, value);
    }


    /**
     * 为key 的值加上 double delta. 原来的值必须是能转换成Integer类型的。否则会抛出异常
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public <T> Double increment(HashOperations<String, String, T> hashOperations, String key, String hashKey, double delta) {
        return hashOperations.increment(key, hashKey, delta);
    }

    /**
     * 为散了中某个值加上 long delta. 原来的值必须是能转换成Integer类型的。否则会抛出异常
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public <T> Long increment(HashOperations<String, String, T> hashOperations, String key, String hashKey, long delta) {
        return hashOperations.increment(key, hashKey, delta);
    }


    /**
     * 删除某个key
     *
     * @param key
     * @param hashKey
     * @return
     */
    public <T> Long remove(HashOperations<String, String, T> hashOperations, String key, String... hashKey) {

        return hashOperations.delete(key, hashKey);
    }

    /**
     * 删除某个key
     *
     * @param key
     * @param hashKey
     * @return
     */
    public <T> Boolean hasKey(HashOperations<String, String, T> hashOperations, String key, String hashKey) {

        return hashOperations.hasKey(key, hashKey);
    }
}
