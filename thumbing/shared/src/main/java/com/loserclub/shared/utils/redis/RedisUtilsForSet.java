package com.loserclub.shared.utils.redis;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.SetOperations;

import java.util.List;
import java.util.Set;


/**
 * @author Stan Sai
 * @date 2020-06-29
 */
@Slf4j
@Data
public class RedisUtilsForSet {

    SetOperations<String, String> setOperations;

    private static RedisUtilsForSet INSTANCE = new RedisUtilsForSet();

    private static Object lock = new Object();

    private RedisUtilsForSet() {

    }

    /**
     * 多线程下的单例模式
     * 保证setOperations不会被初始化两次
     *
     * @param setOperations
     * @return
     */
    public static RedisUtilsForSet getInstance(SetOperations setOperations) {
        if (INSTANCE.getSetOperations() == null) {
            synchronized (lock) {
                if (INSTANCE.getSetOperations() == null) {
                    INSTANCE.setSetOperations(setOperations);
                }
            }
        }
        return INSTANCE;
    }

    public static RedisUtilsForSet getInstance() {
        if (INSTANCE.getSetOperations() == null) {
            return null;
        }
        return INSTANCE;
    }

    /**
     * 给集合key添加多个值，集合不存在创建后再添加
     *
     * @param key
     * @param value
     * @return
     */
    public Long add(String key, String... value) {
        return setOperations.add(key, value);
    }

    /**
     * 移除集合中多个value值
     *
     * @param key
     * @param value
     * @return
     */
    public Long remove(String key, String... value) {
        return setOperations.remove(key, value);
    }

    /**
     * 集合中随机删除一个值并返回
     *
     * @param key
     * @return
     */
    public String pop(String key) {
        String val = setOperations.pop(key);
        return val;
    }

    /**
     * 把源集合中的一个元素移动到目标集合。成功返回true.
     *
     * @param sourceKey
     * @param value
     * @param destKey
     * @return
     */
    public Boolean move(String sourceKey, String value, String destKey) {
        return setOperations.move(sourceKey, value, destKey);
    }

    /**
     * 返回集合的大小
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return setOperations.size(key);
    }


    /**
     * 检查集合中是否存在某个元素
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean isExist(String key, String value) {
        return setOperations.isMember(key, value);
    }


    /**
     * 求两个集合的交集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> intersect(String key, String otherKey) {
        return setOperations.intersect(key, otherKey);
    }

    /**
     * 求多个集合的交集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> intersect(String key, List<String> otherKeys) {
        return setOperations.intersect(key, otherKeys);
    }

    /**
     * 取两个集合的交集并且存到目标集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return 返回目标集合的长度
     */
    public Long intersectAndStore(String key, String otherKey, String destKey) {
        return setOperations.intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 取多个集合的交集并且存到目标集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return 返回目标集合的长度
     */
    public Long intersectAndStore(String key, List<String> otherKeys, String destKey) {
        return setOperations.intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 求两个集合的并集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> Union(String key, String otherKey) {
        return setOperations.union(key, otherKey);
    }

    /**
     * 求多个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> union(String key, List<String> otherKeys) {
        return setOperations.union(key, otherKeys);
    }

    /**
     * 取两个集合的并集并且存到目标集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return 返回目标集合的长度
     */
    public Long unionAndStore(String key, String otherKey, String destKey) {
        return setOperations.unionAndStore(key, otherKey, destKey);
    }

    /**
     * 取多个集合的并集并且存到目标集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return 返回目标集合的长度
     */
    public Long unionAndStore(String key, List<String> otherKeys, String destKey) {
        return setOperations.unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 求两个集合的差集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> difference(String key, String otherKey) {
        return setOperations.difference(key, otherKey);
    }

    /**
     * 求多个集合的差集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> difference(String key, List<String> otherKeys) {
        return setOperations.difference(key, otherKeys);
    }

    /**
     * 取两个集合的差集并且存到目标集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return 返回目标集合的长度
     */
    public Long differenceAndStore(String key, String otherKey, String destKey) {
        return setOperations.differenceAndStore(key, otherKey, destKey);
    }

    /**
     * 取多个集合的差集并且存到目标集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return 返回目标集合的长度
     */
    public Long differenceAndStore(String key, List<String> otherKeys, String destKey) {
        return setOperations.differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取集合中的所有元素
     *
     * @param key
     * @return
     */
    public Set<String> members(String key) {
        return setOperations.members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key
     * @return
     */
    public String randomMember(String key) {
        String val = setOperations.randomMember(key);
        return  val;
    }

    /**
     * 随机返回集合中指定个数的不同元素
     *
     * @param key
     * @param count
     * @return
     */
    public Set<String> distinctRandomMembers(String key, long count) {
        return setOperations.distinctRandomMembers(key, count);
    }

    /**
     * 随机返回集合中指定个数的元素，随机元素可能相同
     *
     * @param key
     * @param count
     * @return
     */
    public List<String> randomMembers(String key, long count) {
        return setOperations.randomMembers(key, count);
    }

}
