package com.thumbing.shared.utils.redis;

import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

@Data
@Slf4j
@UtilityClass
public class RedisUtilsForZSet {

    /**
     * 给有序集合添加一个指定分数的成员 如果成员存在则覆盖
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public <T> Boolean add(ZSetOperations<String, T> zSetOperations, String key, T value, double score) {
        return zSetOperations.add(key, value, score);
    }

    /**
     * 往有序集合中添加成员集合
     * DefaultTypedTuple(@Nullable V value, @Nullable Double score)
     * TypedTuple{
     *
     * @param key
     * @param set
     * @return 集合长度
     * @Nullable V getValue();
     * @Nullable Double getScore();
     * }
     */
    public <T> Long add(ZSetOperations<String, T> zSetOperations, String key, Set<ZSetOperations.TypedTuple<T>> set) {
        return zSetOperations.add(key, set);
    }

    /**
     * 移除有序集合中指定的多个成员. 如果成员不存在则忽略
     *
     * @param key
     * @param value
     * @return 集合长度
     */
    public <T> Long remove(ZSetOperations<String, T> zSetOperations, String key, T... value) {
        return zSetOperations.remove(key, value);
    }

    /**
     * 给有序集合中的指定成员的分数增加 score
     *
     * @param key
     * @param value
     * @param score
     * @return 分数
     */
    public <T> Double incrementScore(ZSetOperations<String, T> zSetOperations, String key, T value, double score) {
        return zSetOperations.incrementScore(key, value, score);
    }

    /**
     * 从0开始计算并返回成员在有序集合中从低到高的排名
     *
     * @param key
     * @param value
     * @return
     */
    public <T> Long rank(ZSetOperations<String, T> zSetOperations, String key, T value) {
        return zSetOperations.rank(key, value);
    }

    /**
     * 从0开始计算并返回成员在有序集合中从高到低的排名
     *
     * @param key
     * @param value
     * @return
     */
    public <T> Long reserveRank(ZSetOperations<String, T> zSetOperations, String key, T value) {
        return zSetOperations.rank(key, value);
    }

    /**
     * 从有序集合中获取指定范围内从高到低的成员集合
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public <T> Set<T> range(ZSetOperations<String, T> zSetOperations, String key, long start, long end) {
        return zSetOperations.range(key, start, end);
    }

    /**
     * 从有序集合中获取指定范围内从高到低的带有分数的成员集合
     *
     * @param key
     * @param start
     * @param end
     * @returnT
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> rangeWithScores(ZSetOperations<String, T> zSetOperations, String key, long start, long end) {
        return zSetOperations.rangeWithScores(key, start, end);
    }

    /**
     * 获取有序集合中分数在指定的最小值 与最大值之间的所有成员集合  闭合区间
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Set<T> rangeByScore(ZSetOperations<String, T> zSetOperations, String key, double min, double max) {
        return zSetOperations.rangeByScore(key, min, max);
    }

    /**
     * 获取有序集合中分数在指定的最小值 与最大值之间的所有带有分数的成员集合  闭合区间
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> rangeByScoreWithScores(ZSetOperations<String, T> zSetOperations, String key, double min, double max) {
        return zSetOperations.rangeByScoreWithScores(key, min, max);
    }

    /**
     * 从指定位置开始获取有序集合中分数在指定的最小值 与最大值之间的指定数量的成员集合  闭合区间
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Set<T> rangeByScore(ZSetOperations<String, T> zSetOperations, String key, double min, double max, long offSet, long count) {
        return zSetOperations.rangeByScore(key, min, max, offSet, count);
    }

    /**
     * 从指定位置开始获取有序集合中分数在指定的最小值 与最大值之间的指定数量的带有分数的成员集合  闭合区间
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> rangeByScoreWithScores(ZSetOperations<String, T> zSetOperations, String key, double min, double max, long offSet, long count) {
        return zSetOperations.rangeByScoreWithScores(key, min, max, offSet, count);
    }

    /**
     * 从有序集合中获取指定范围内从低到高的成员集合
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public <T> Set<T> reverseRange(ZSetOperations<String, T> zSetOperations, String key, long start, long end) {
        return zSetOperations.reverseRange(key, start, end);
    }

    /**
     * 从有序集合中获取指定范围内从低到高的带有分数的成员集合
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> reverseRangeWithScores(ZSetOperations<String, T> zSetOperations, String key, long start, long end) {
        return zSetOperations.reverseRangeWithScores(key, start, end);
    }

    /**
     * 获取有序集合中分数在指定的最小值 与最大值之间的从高到低的所有成员集合  闭合区间
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Set<T> reverseRangeByScore(ZSetOperations<String, T> zSetOperations, String key, double min, double max) {
        return zSetOperations.reverseRangeByScore(key, min, max);
    }

    /**
     * 获取有序集合中分数在指定的最小值 与最大值之间的从高到低的所有带有分数的成员集合  闭合区间
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> reverseRangeByScoreWithScores(ZSetOperations<String, T> zSetOperations, String key, double min, double max) {
        return zSetOperations.reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * 从指定位置开始获取有序集合中分数在指定的最小值 与最大值之间的从高到低指定数量的成员集合  闭合区间
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Set<T> reverseRangeByScore(ZSetOperations<String, T> zSetOperations, String key, double min, double max, long offSet, long count) {
        return zSetOperations.reverseRangeByScore(key, min, max, offSet, count);
    }

    /**
     * 从指定位置开始获取有序集合中分数在指定的最小值 与最大值之间的从高到低指定数量的带有分数的成员集合  闭合区间
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> reverseRangeByScoreWithScores(ZSetOperations<String, T> zSetOperations, String key, double min, double max, long offSet, long count) {
        return zSetOperations.reverseRangeByScoreWithScores(key, min, max, offSet, count);
    }

    /**
     * 统计分数在范围内的成员个数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Long count(ZSetOperations<String, T> zSetOperations, String key, double min, double max) {
        return zSetOperations.count(key, min, max);
    }

    /**
     * 返回有序集合的大小
     *
     * @param key
     * @return
     */
    public <T> Long size(ZSetOperations<String, T> zSetOperations, String key) {
        return zSetOperations.zCard(key);
    }

    /**
     * 获取有序集合中某个成员的分数
     *
     * @param Key
     * @param value
     * @return
     */
    public <T> Double score(ZSetOperations<String, T> zSetOperations, String Key, String value) {
        return zSetOperations.score(Key, value);
    }


    /**
     * 移除有序集合中从start开始到end结束的成员 闭区间
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public <T> Long removeRange(ZSetOperations<String, T> zSetOperations, String key, long start, long end) {
        return zSetOperations.removeRange(key, start, end);
    }

    /**
     * 移除有序集合中分数在指定范围内[min,max]的成员
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public <T> Long removeRangeByScore(ZSetOperations<String, T> zSetOperations, String key, double min, double max) {
        return zSetOperations.removeRangeByScore(key, min, max);
    }

    /**
     * 取两个集合的并集并且存到目标集合中，如果存在相同成员，则分数相加
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return 返回目标集合的长度
     */
    public <T> Long unionAndStore(ZSetOperations<String, T> zSetOperations, String key, String otherKey, String destKey) {
        return zSetOperations.unionAndStore(key, otherKey, destKey);
    }

    /**
     * 取多个集合的并集并且存到目标集合中，如果存在相同成员，则分数相加
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return 返回目标集合的长度
     */
    public <T> Long unionAndStore(ZSetOperations<String, T> zSetOperations, String key, List<String> otherKeys, String destKey) {
        return zSetOperations.unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 取两个集合的交集并且分数相加存到目标集合中，没有共同的成员则忽略
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return 返回目标集合的长度
     */
    public <T> Long intersectAndStore(ZSetOperations<String, T> zSetOperations, String key, String otherKey, String destKey) {
        return zSetOperations.intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 取多个集合的交集并且分数相加存到目标集合中，没有共同的成员则忽略
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return 返回目标集合的长度
     */
    public <T> Long intersectAndStore(ZSetOperations<String, T> zSetOperations, String key, List<String> otherKeys, String destKey) {
        RedisZSetCommands.Range range;
        return zSetOperations.intersectAndStore(key, otherKeys, destKey);
    }

}
