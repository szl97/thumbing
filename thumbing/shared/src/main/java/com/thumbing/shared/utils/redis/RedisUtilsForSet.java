package com.thumbing.shared.utils.redis;

import lombok.Data;
import lombok.experimental.UtilityClass;
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
@UtilityClass
public class RedisUtilsForSet {
    /**
     * 给集合key添加多个值，集合不存在创建后再添加
     *
     * @param key
     * @param value
     * @return
     */
    public <T> Long add(SetOperations<String, T> setOperations, String key, T... value) {
        return setOperations.add(key, value);
    }

    /**
     * 移除集合中多个value值
     *
     * @param key
     * @param value
     * @return
     */
    public <T> Long remove(SetOperations<String, T> setOperations, String key, T... value) {
        return setOperations.remove(key, value);
    }

    /**
     * 集合中随机删除一个值并返回
     *
     * @param key
     * @return
     */
    public <T> T pop(SetOperations<String, T> setOperations, String key) {
        T val = setOperations.pop(key);
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
    public <T> Boolean move(SetOperations<String, T> setOperations, String sourceKey, T value, String destKey) {
        return setOperations.move(sourceKey, value, destKey);
    }

    /**
     * 返回集合的大小
     *
     * @param key
     * @return
     */
    public <T> Long size(SetOperations<String, T> setOperations, String key) {
        return setOperations.size(key);
    }


    /**
     * 检查集合中是否存在某个元素
     *
     * @param key
     * @param value
     * @return
     */
    public <T> Boolean isExist(SetOperations<String, T> setOperations, String key, T value) {
        return setOperations.isMember(key, value);
    }


    /**
     * 求两个集合的交集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public <T> Set<T> intersect(SetOperations<String, T> setOperations, String key, String otherKey) {
        return setOperations.intersect(key, otherKey);
    }

    /**
     * 求多个集合的交集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public <T> Set<T> intersect(SetOperations<String, T> setOperations, String key, List<String> otherKeys) {
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
    public <T> Long intersectAndStore(SetOperations<String, T> setOperations, String key, String otherKey, String destKey) {
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
    public <T> Long intersectAndStore(SetOperations<String, T> setOperations, String key, List<String> otherKeys, String destKey) {
        return setOperations.intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 求两个集合的并集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public <T> Set<T> Union(SetOperations<String, T> setOperations, String key, String otherKey) {
        return setOperations.union(key, otherKey);
    }

    /**
     * 求多个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public <T> Set<T> union(SetOperations<String, T> setOperations, String key, List<String> otherKeys) {
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
    public <T> Long unionAndStore(SetOperations<String, T> setOperations, String key, String otherKey, String destKey) {
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
    public <T> Long unionAndStore(SetOperations<String, T> setOperations, String key, List<String> otherKeys, String destKey) {
        return setOperations.unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 求两个集合的差集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public <T> Set<T> difference(SetOperations<String, T> setOperations, String key, String otherKey) {
        return setOperations.difference(key, otherKey);
    }

    /**
     * 求多个集合的差集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public <T> Set<T> difference(SetOperations<String, T> setOperations, String key, List<String> otherKeys) {
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
    public <T> Long differenceAndStore(SetOperations<String, T> setOperations, String key, String otherKey, String destKey) {
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
    public <T> Long differenceAndStore(SetOperations<String, T> setOperations, String key, List<String> otherKeys, String destKey) {
        return setOperations.differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取集合中的所有元素
     *
     * @param key
     * @return
     */
    public <T> Set<T> members(SetOperations<String, T> setOperations, String key) {
        return setOperations.members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key
     * @return
     */
    public <T> T randomMember(SetOperations<String, T> setOperations, String key) {
        T val = setOperations.randomMember(key);
        return  val;
    }

    /**
     * 随机返回集合中指定个数的不同元素
     *
     * @param key
     * @param count
     * @return
     */
    public <T> Set<T> distinctRandomMembers(SetOperations<String, T> setOperations, String key, long count) {
        return setOperations.distinctRandomMembers(key, count);
    }

    /**
     * 随机返回集合中指定个数的元素，随机元素可能相同
     *
     * @param key
     * @param count
     * @return
     */
    public <T> List<T> randomMembers(SetOperations<String, T> setOperations, String key, long count) {
        return setOperations.randomMembers(key, count);
    }

}
