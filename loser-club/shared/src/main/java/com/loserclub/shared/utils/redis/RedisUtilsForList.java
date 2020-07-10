package com.loserclub.shared.utils.redis;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Stan Sai
 * @date 2020-06-29
 */
@Slf4j
@Data
public class RedisUtilsForList {

    ListOperations<String, Serializable> listOperations;

    private static RedisUtilsForList INSTANCE = new RedisUtilsForList();

    private static Object lock = new Object();

    private RedisUtilsForList() {

    }

    /**
     * 多线程下的单例模式
     * 保证listOperations不会被初始化两次
     *
     * @param listOperations
     * @return
     */
    public static RedisUtilsForList getInstance(ListOperations listOperations) {
        if (INSTANCE.getListOperations() == null) {
            synchronized (lock) {
                if (INSTANCE.getListOperations() == null) {
                    INSTANCE.setListOperations(listOperations);
                }
            }
        }
        return INSTANCE;
    }

    public static RedisUtilsForList getInstance() {
        if (INSTANCE.getListOperations() == null) {
            return null;
        }
        return INSTANCE;
    }

    /**
     * 获取整个list
     *
     * @param key
     * @return
     */
    public List<Serializable> getAll(String key) {
        return listOperations.range(key, 0, -1);
    }

    /**
     * 获取部分list
     *
     * @param key
     * @return
     */
    public List<Serializable> get(String key, long start, long end) {
        return listOperations.range(key, start, end);
    }

    /**
     * 保留key指定范围内的列表值。其它的都删除
     *
     * @param key
     * @param start
     * @param end
     */
    public void clearAndPersist(String key, long start, long end) {
        listOperations.trim(key, start, end);
    }

    /**
     * 获取list的长度
     *
     * @param key
     * @return 列表插入后的长度
     */
    public Long size(String key) {
        return listOperations.size(key);
    }

    /**
     * 从列表左边（头部）加入
     *
     * @param key
     * @param value
     * @return 列表插入后的长度
     */
    public Long leftPush(String key, Serializable value) {
        return listOperations.leftPush(key, value);
    }

    /**
     * 从列表左边（头部）加入
     *
     * @param key
     * @param value
     * @return 列表插入后的长度
     */
    public Long leftPush(String key, Serializable... value) {
        return listOperations.leftPushAll(key, value);
    }

    /**
     * 从列表左边（头部）加入
     *
     * @param key
     * @param value
     * @return 列表插入后的长度
     */
    public Long leftPush(String key, List<Serializable> value) {
        return listOperations.leftPushAll(key, value);
    }

    /**
     * 如果列表存在，则从列表左边（头部）加入
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean leftPushNx(String key, Serializable value) {
        Long origin = size(key);
        return origin == listOperations.leftPushIfPresent(key, value) - 1;
    }

    /**
     * 在指定的next的前面插入value
     * 如果next不存在则不插入
     *
     * @param key
     * @param next
     * @param value
     * @return
     */
    public Boolean leftPush(String key, Serializable next, Serializable value) {
        Long origin = size(key);
        return origin == listOperations.leftPush(key, next, value) - 1;
    }

    /**
     * 从列表右边（尾部）加入
     *
     * @param key
     * @param value
     * @return 列表插入后的长度
     */
    public Long rightPush(String key, Serializable value) {
        return listOperations.rightPush(key, value);
    }

    /**
     * 从列表右边（尾部）加入
     *
     * @param key
     * @param value
     * @return 列表插入后的长度
     */
    public Long rightPush(String key, Serializable... value) {
        return listOperations.rightPushAll(key, value);
    }

    /**
     * 从列表右边（尾部）加入
     *
     * @param key
     * @param value
     * @return 列表插入后的长度
     */
    public Long rightPush(String key, List<Serializable> value) {
        return listOperations.rightPushAll(key, value);
    }

    /**
     * 如果列表存在，则从列表右边（尾部）加入
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean rightPushNx(String key, Serializable value) {
        Long origin = size(key);
        return origin == listOperations.rightPushIfPresent(key, value) - 1;
    }

    /**
     * 在指定的next的后面插入value
     * 如果next不存在则不插入
     *
     * @param key
     * @param next
     * @param value
     * @return
     */
    public Boolean rightPush(String key, Serializable next, Serializable value) {
        Long origin = size(key);
        return origin == listOperations.rightPush(key, next, value) - 1;
    }

    /**
     * 设置key列表中指定位置的值为value index不能大于列表长度。大于抛出异常
     * index为负数则从右边开始计算
     *
     * @param key
     * @param index
     * @param value
     */
    public void setListValue(String key, long index, Serializable value) {
        listOperations.set(key, index, value);
    }

    /**
     * 删除列表中第一个遇到的value值。count指定删除多少个
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long remove(String key, long count, Serializable value) {
        Long origin = size(key);
        Long newSize = listOperations.remove(key, count, value);
        return origin - newSize;
    }

    /**
     * 获取列表中指定索引的value
     *
     * @param key
     * @param index
     * @param <T>
     * @return
     */
    public <T extends Serializable> T get(String key, long index) {
        Serializable val = listOperations.index(key, index);
        if (val == null) {
            return null;
        }
        return ((T) val);
    }


    /**
     * 移除列表中的第一个值，并返回该值
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T extends Serializable> T leftPop(String key) {
        Serializable val = listOperations.leftPop(key);
        if (val == null) {
            return null;
        }
        return ((T) val);
    }

    /**
     * 堵塞版的leftPop，移除列表中的第一个值，并返回该值
     * 如果列表为空，则堵塞一定时长
     * 可用于消息的订阅和发布
     *
     * @param key
     * @param times
     * @param unit
     * @param <T>
     * @return
     */
    public <T extends Serializable> T leftPop(String key, long times, TimeUnit unit) {
        Serializable val = listOperations.leftPop(key, times, unit);
        if (val == null) {
            return null;
        }
        return ((T) val);
    }

    /**
     * 移除列表中的倒数第一个值，并返回该值
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T extends Serializable> T rightPop(String key) {
        Serializable val = listOperations.rightPop(key);
        if (val == null) {
            return null;
        }
        return ((T) val);
    }

    /**
     * 堵塞版的rightPop，移除列表中的倒数第一个值，并返回该值
     * 如果列表为空，则堵塞一定时长
     * 可用于消息的订阅和发布
     *
     * @param key
     * @param times
     * @param unit
     * @param <T>
     * @return
     */
    public <T extends Serializable> T rightPop(String key, long times, TimeUnit unit) {
        Serializable val = listOperations.rightPop(key, times, unit);
        if (val == null) {
            return null;
        }
        return ((T) val);
    }

    /**
     * 将sourceKey对应列表的最后一个值删除后加到destKey对应的列表
     * 相当于将消息传递给另一个订阅者
     *
     * @param sourceKey
     * @param destKey
     * @param <T>
     * @return
     */
    public <T extends Serializable> T rightPopAndLeftPush(String sourceKey, String destKey) {
        Serializable val = listOperations.rightPopAndLeftPush(sourceKey, destKey);
        if (val == null) {
            return null;
        }
        return ((T) val);
    }

    /**
     * 堵塞版的rightPopAndLeftPush
     * 将sourceKey对应列表的最后一个值删除后加到destKey对应的列表
     * 如果sourceKey对应列表为空，则等待一段时间
     * 相当于将消息传递给另一个订阅者
     *
     * @param sourceKey
     * @param destKey
     * @param <T>
     * @return
     */
    public <T extends Serializable> T rightPopAndLeftPush(String sourceKey, String destKey, long times, TimeUnit unit) {
        Serializable val = listOperations.rightPopAndLeftPush(sourceKey, destKey, times, unit);
        if (val == null) {
            return null;
        }
        return ((T) val);
    }
}
