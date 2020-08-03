package com.thumbing.shared.utils.dozermapper;

/**
 * @author Stan Sai
 * @date 2020/8/3 15:26
 */
public interface DozerMapperWrapper<S, T>{
    void accept(S s, T t);
}
