package com.thumbing.shared.utils.dozermapper;

import com.github.dozermapper.core.Mapper;
import com.thumbing.shared.dto.output.PageResultDto;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stan Sai
 * @date 2020/8/3 15:29
 */
public class DozerUtils {
    /**
     * 封装dozer处理集合的方法
     */
    public static <T, S> List<T> mapList(final Mapper mapper, List<S> sourceList, Class<T> targetObjectClass) {
      return sourceList.parallelStream().map(e -> mapper.map(e, targetObjectClass))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }


    /**
     * 封装dozer处理集合的方法
     */
    public static <T, S> List<T> mapList(final Mapper mapper, List<S> sourceList, Class<T> targetObjectClass, DozerMapperWrapper<S, T> action) {
        return sourceList.parallelStream().map(e ->{
           var t =  mapper.map(e, targetObjectClass);
           action.accept(e,t);
           return t;
        }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * 封装dozer处理集合的方法
     */
    public static <T, S> List<T> mapListSync(final Mapper mapper, List<S> sourceList, Class<T> targetObjectClass) {
        return sourceList.stream().map(e -> mapper.map(e, targetObjectClass))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }


    /**
     * 封装dozer处理集合的方法
     */
    public static <T, S> List<T> mapListSync(final Mapper mapper, List<S> sourceList, Class<T> targetObjectClass, DozerMapperWrapper<S, T> action) {
        return sourceList.stream().map(e ->{
            var t =  mapper.map(e, targetObjectClass);
            action.accept(e,t);
            return t;
        }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public static <T, S> PageResultDto<T> mapToPagedResultDto(final Mapper mapper, Page<S> page, Class<T> targetObjectClass){
        List<T> list = DozerUtils.mapList(mapper, page.getContent(), targetObjectClass);
        return new PageResultDto<>(page.getTotalElements(), list);
    }

    public static <T, S> PageResultDto<T> mapToPagedResultDto(final Mapper mapper, Page<S> page, Class<T> targetObjectClass, DozerMapperWrapper<S, T> action){
        List<T> list = DozerUtils.mapList(mapper, page.getContent(), targetObjectClass, action);
        return new PageResultDto<>(page.getTotalElements(), list);
    }

    public static <T, S> PageResultDto<T> mapToPagedResultDtoSync(final Mapper mapper, Page<S> page, Class<T> targetObjectClass){
        List<T> list = DozerUtils.mapListSync(mapper, page.getContent(), targetObjectClass);
        return new PageResultDto<>(page.getTotalElements(), list);
    }

    public static <T, S> PageResultDto<T> mapToPagedResultDtoSync(final Mapper mapper, Page<S> page, Class<T> targetObjectClass, DozerMapperWrapper<S, T> action){
        List<T> list = DozerUtils.mapListSync(mapper, page.getContent(), targetObjectClass, action);
        return new PageResultDto<>(page.getTotalElements(), list);
    }
}
