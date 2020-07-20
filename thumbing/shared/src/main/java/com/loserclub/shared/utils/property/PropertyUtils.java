package com.loserclub.shared.utils.property;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
public class PropertyUtils {
    /**
     * 拷贝非空值
     *
     * @param source
     * @param target
     */
    public static void copyNotNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }


    /**
     * 获取值为空的属性名称
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || srcValue instanceof Collection)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 获取自己及父类所有私有属性
     *
     * @param c
     * @return
     */
    public static Field[] getAllFields(Class c) {
        Field[] result = null;
        for (; c != Object.class; c = c.getSuperclass()) {
            result = ArrayUtils.addAll(result, c.getDeclaredFields());
        }
        return result;
    }
}
