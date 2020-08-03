package com.thumbing.shared.utils.dozermapper;

import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stan Sai
 * @date 2020/8/3 15:29
 */
public class DozerUtils {
    /**
     * 封装dozer处理集合的方法
     */
    public static <T, S> List<T> mapList(final Mapper mapper, List<S> sourceList, Class<T> targetObjectClass) {
        List<T> targetList = new ArrayList<T>();
        sourceList.forEach(e -> {
            targetList.add(mapper.map(e, targetObjectClass));
        });
        return targetList;
    }

    /**
     * 封装dozer处理集合的方法
     */
    public static <T, S> List<T> mapList(final Mapper mapper, List<S> sourceList, Class<T> targetObjectClass, DozerMapperWrapper<S, T> action) {
        List<T> targetList = new ArrayList<T>();
        sourceList.forEach(s -> {
            var t = mapper.map(s, targetObjectClass);
            action.accept(s, t);
            targetList.add(t);
        });
        return targetList;
    }
}
