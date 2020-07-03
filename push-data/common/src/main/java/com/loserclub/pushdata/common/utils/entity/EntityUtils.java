package com.loserclub.pushdata.common.utils.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;


/**
 * @author Stan Sai
 * @date 2020-06-28
 */
@Slf4j
public class EntityUtils {

    public static Map<Field, Object> notNullCastToMap(Object object) {
        try {
            Map<Field, Object> map = new HashMap();
            for (Field field : getAllFields(object.getClass())) {
                boolean flag = field.canAccess(object);
                field.setAccessible(true);
                Object o = field.get(object);
                if (o != null) {
                    if (o instanceof String && !StringUtils.hasText((String) o)) {
                        continue;
                    }
                    map.put(field, o);
                }
                field.setAccessible(flag);
            }
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static List<Field> getAllFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }


    public static Field getAnnotationField(String fieldName, Class clazz, Class annotation) {
        List<Field> allFields = EntityUtils.getAllFields(clazz);
        for (Field field : allFields) {
            if (field.getName().equals(fieldName)) {
                if (field.getAnnotation(annotation) != null) {
                    return field;
                }
            }
        }
        return null;
    }

    public static Field getField(String fieldName, Class clazz) {
        List<Field> allFields = EntityUtils.getAllFields(clazz);
        for (Field field : allFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    public static void setFieldValue(Field field, Object value, Object target) {
        try {
            boolean flag = field.canAccess(target);
            field.setAccessible(true);
            field.set(target, value);
            field.setAccessible(flag);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    public static void setFieldValue(String fieldName, Object value, Object target) {
        Field field = getField(fieldName, target.getClass());
        if (field != null && value != null) {
            setFieldValue(field, value, target);
        }
    }

    /**
     * 包含注定注解的字段才赋值
     *
     * @param fieldName
     * @param value
     * @param target
     * @param annotation
     */
    public static void setFieldValue(String fieldName, Object value, Object target, Class annotation) {
        Field field = getField(fieldName, target.getClass());
        if (field != null && value != null && field.getAnnotation(annotation) != null) {
            setFieldValue(field, value, target);
        }
    }
}
