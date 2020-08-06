package com.thumbing.shared.uniqueness;

import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-08-05 21:42
 */
@UtilityClass
public class UniqueLockKeyContextHolder {
    private static ThreadLocal<List<String>> THREAD_LOCAL_LOCK_KEY = new ThreadLocal<>();

    public List<String> getKeys() {
        return THREAD_LOCAL_LOCK_KEY.get();
    }

    public void setKeys(List<String> keys) {
        THREAD_LOCAL_LOCK_KEY.set(keys);
    }

    public void clear() {
        THREAD_LOCAL_LOCK_KEY.remove();
    }
}
