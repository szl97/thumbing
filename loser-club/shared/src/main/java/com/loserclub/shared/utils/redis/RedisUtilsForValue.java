package com.loserclub.shared.utils.redis;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Stan Sai
 * @date 2020-06-29
 */
@Slf4j
@Data
public class RedisUtilsForValue {

    ValueOperations<String, String> valueOperations;

    private static RedisUtilsForValue INSTANCE = new RedisUtilsForValue();

    private static Object lock = new Object();

    private RedisUtilsForValue() {

    }

    /**
     * 多线程下的单例模式
     * 保证valueOperations不会被初始化两次
     *
     * @param valueOperations
     * @return
     */
    public static RedisUtilsForValue getInstance(ValueOperations valueOperations) {
        if (INSTANCE.getValueOperations() == null) {
            synchronized (lock) {
                if (INSTANCE.getValueOperations() == null) {
                    INSTANCE.setValueOperations(valueOperations);
                }
            }
        }
        return INSTANCE;
    }

    public static RedisUtilsForValue getInstance() {
        if (INSTANCE.getValueOperations() == null) {
            return null;
        }
        return INSTANCE;
    }

    /**
     * 根据 key 获取对应的value 如果key不存在则返回null
     *
     * @param key
     * @return
     */
    public String getObject(String key) {
        return valueOperations.get(key);
    }


    /**
     * 根据 key 获取指定类型的value 如果key不存在则返回null
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String val = valueOperations.get(key);
        return val;
    }


    /**
     * 获取原值，并设置新值
     *
     * @param key
     * @return
     */
    public String getAndSet(String key, String value) {
        String val = valueOperations.getAndSet(key, value);
        return  val;
    }

    /**
     * 根据提供的key集合按顺序获取对应的value值
     *
     * @param keys
     * @return
     */
    public List<String> get(List<String> keys) {
        List<String> val = valueOperations.multiGet(keys);
        return val;
    }

    /**
     * 获取key 值从 start位置开始到end位置结束。 等于String 的 subString 前后闭区间
     * 0 -1 整个key的值
     * -4 -1 从尾部开始往前截长度为4
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String get(String key, long start, long end) {
        String val = valueOperations.get(key, start, end);
        if (val == null) {
            return null;
        }
        return val;
    }

    /**
     * 获取value的大小
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return valueOperations.size(key);
    }

    ;


    /**
     * 如果key不存在添加key 保存值为value
     * 如果key存在则对value进行覆盖
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        valueOperations.set(key, value);
    }


    /**
     * 如果key不存在添加key 保存值为value
     * 如果key存在则对value进行覆盖
     * 并设置过期时间
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public void setWithExpireTime(String key, String value, long timeout, TimeUnit unit) {
        valueOperations.set(key, value, timeout, unit);
    }


    /**
     * 把一个map的键值对添加到redis中，key-value 对应着 key value。如果key已经存在就覆盖，
     *
     * @param map
     */
    public void set(Map<String, String> map) {

        valueOperations.multiSet(map);
    }


    /**
     * 在某个位置上开始覆盖原有的值，对于空值保存空格
     *
     * @param key
     * @param value
     * @param offset 偏移量
     */
    public void set(String key, String value, long offset) {
        valueOperations.set(key, value, offset);
    }

    /**
     * 如果key不存在，则设置key 的值为 value. 存在则不设置
     * 设置成功返回true 失败返回false
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean setNx(String key, String value) {
        return valueOperations.setIfAbsent(key, value);
    }


    /**
     * 如果key不存在，则设置key 的值为 value. 存在则不设置
     * 设置成功返回true 失败返回false
     * 并设置过期时间
     * 此方法用于设置分布式锁
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public Boolean setNx(String key, String value, long timeout, TimeUnit unit) {
        return valueOperations.setIfAbsent(key, value, timeout, unit);
    }


    /**
     * 为 key的值末尾追加 value 如果key不存在就直接等于 set(K key, V value)
     *
     * @param key
     * @param value
     */
    public Integer append(String key, String value) {
        return valueOperations.append(key, value);
    }

    /**
     * 为key 的值加上 long delta. 原来的值必须是能转换成Integer类型的。否则会抛出异常
     *
     * @param key
     * @param delta
     * @return
     */
    public Long increment(String key, long delta) {
        return valueOperations.increment(key, delta);
    }


    /**
     * 为散了中某个值加上 double delta. 原来的值必须是能转换成Integer类型的。否则会抛出异常
     *
     * @param key
     * @param delta
     * @return
     */
    public Double increment(String key, double delta) {
        return valueOperations.increment(key, delta);
    }

    /**
     * 设置key的值偏移量为offset的bit位上的值为0或者1.true:1 false:0
     *
     * @param key
     * @param offset
     * @param value
     * @return
     */
    public Boolean setBit(String key, long offset, boolean value) {
        return valueOperations.setBit(key, offset, value);
    }

    /**
     * 获取key的值偏移量offset的bit位的值。 返回true or false
     *
     * @param key
     * @param offset
     * @return
     */
    public Boolean getBit(String key, long offset) {
        return valueOperations.getBit(key, offset);
    }
}
