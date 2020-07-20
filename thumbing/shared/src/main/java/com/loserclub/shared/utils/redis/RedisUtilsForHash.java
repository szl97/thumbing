package com.loserclub.shared.utils.redis;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-06-29
 */
@Slf4j
@Data
public class RedisUtilsForHash {

    HashOperations<String, String, String> hashOperations;

    private static RedisUtilsForHash INSTANCE = new RedisUtilsForHash();

    private static Object lock = new Object();

    private RedisUtilsForHash() {

    }

    /**
     * 多线程下的单例模式
     * 保证hashOperations不会被初始化两次
     *
     * @param hashOperations
     * @return
     */
    public static RedisUtilsForHash getInstance(HashOperations hashOperations) {
        if (INSTANCE.getHashOperations() == null) {
            synchronized (lock) {
                if (INSTANCE.getHashOperations() == null) {
                    INSTANCE.setHashOperations(hashOperations);
                }
            }
        }
        return INSTANCE;
    }

    public static RedisUtilsForHash getInstance() {
        if (INSTANCE.getHashOperations() == null) {
            return null;
        }
        return INSTANCE;
    }

    /**
     * 根据 key 获取散列中对应hash key的value 如果key不存在则返回null
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object getObject(String key, String hashKey) {
        return hashOperations.get(key, hashKey);
    }


    /**
     * 根据 key 获取散列中对应hash key的指定类型的value 如果key不存在则返回null
     *
     * @param key
     * @param hashKey
     * @return
     */
    public String get(String key, String hashKey) {
        String val = hashOperations.get(key, hashKey);
        return  val;
    }


    /**
     * 根据 key 按hash key集合的顺序获取value 如果key不存在则返回null
     *
     * @param key
     * @param hashKeys
     * @return
     */
    public List<String> get(String key, List<String> hashKeys) {
        List<String> val = hashOperations.multiGet(key, hashKeys);
        return val;
    }

    /**
     * 获取散列中所有的hash key集合
     *
     * @param key
     * @return
     */
    public Set<String> getAllKeys(String key) {
        return hashOperations.keys(key);
    }

    /**
     * 获取散列中所有的value
     *
     * @param key
     * @return
     */
    public List<String> getAllValues(String key) {
        return hashOperations.values(key);
    }

    /**
     * 获取散列中所有的键值对
     *
     * @param key
     * @return
     */
    public Map<String, String> getAllEntries(String key) {
        return hashOperations.entries(key);
    }


    /**
     * 获取hash的大小
     *
     * @param key
     * @return
     */
    public Long size(String key) {
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
    public void put(String key, String hashKey, String value) {
        hashOperations.put(key, hashKey, value);
    }

    /**
     * 为散列添加多个key-value键值对
     *
     * @param key
     * @param map
     */
    public void put(String key, Map<String, String> map) {
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
    public Boolean setNx(String key, String hashKey, String value) {
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
    public Double increment(String key, String hashKey, double delta) {
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
    public Long increment(String key, String hashKey, long delta) {
        return hashOperations.increment(key, hashKey, delta);
    }


    /**
     * 删除某个key
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Long remove(String key, String... hashKey) {

        return hashOperations.delete(key, hashKey);
    }

    /**
     * 删除某个key
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Boolean hasKey(String key, String hashKey) {

        return hashOperations.hasKey(key, hashKey);
    }
}
