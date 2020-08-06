package com.thumbing.shared.utils.security;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Assert;

/**
 * @author Stan Sai
 * @date 2020-08-06 19:58
 */
@UtilityClass
public class MD5Utils {
    public String encryptByMd5Jar(String key, String... value) {
        StringBuilder sb = new StringBuilder(key);
        for(String v : value){
            sb.append(v);
        }
        return DigestUtils.md5Hex(sb.toString());
    }

    public Boolean checkEncryption(String key, String encryption, String... value){
         return encryption.equals(encryptByMd5Jar(key,value));
    }
}
