package com.thumbing.shared.utils.cpu;

import lombok.experimental.UtilityClass;

/**
 * @author Stan Sai
 * @date 2020-08-06 20:38
 */
@UtilityClass
public class CoresUtils {
    public int getCores(){
        return Runtime.getRuntime().availableProcessors();
    }
}
