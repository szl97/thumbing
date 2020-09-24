package com.thumbing.shared.utils.wrap;

/**
 * @Author: Stan Sai
 * @Date: 2020/9/24 15:05
 */
public class WrapObject<T> {
    public WrapObject(){
        value = null;
    }
    public WrapObject(T value){
        this.value = value;
    }
    private T value;
    public T getValue(){
        return value;
    }
    public void setValue(T value){
        this.value = value;
    }
}
