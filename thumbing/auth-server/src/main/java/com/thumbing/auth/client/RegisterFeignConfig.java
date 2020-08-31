package com.thumbing.auth.client;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbing.shared.constants.HttpHeaderConstants;
import com.thumbing.shared.exception.BusinessException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/31 14:15
 */
@Configuration
@Slf4j
public class RegisterFeignConfig implements RequestInterceptor {
    private final String USERNAME_HEADER = HttpHeaderConstants.USERNAME_HEADER;
    @Value("${rsa.password}")
    private String password;
    @Value("${rsa.privateKey}")
    private String privateKey;
    @Autowired
    ObjectMapper objectMapper;
    @Override
    public void apply(RequestTemplate template) {
        byte[] bytes2 = template.body();
        if(bytes2.length == 0) throw new BusinessException("参数为空");
        RSA rsa = new RSA(privateKey, null);
        byte[] bytes1 = password.getBytes(CharsetUtil.CHARSET_UTF_8);
        byte[] bytes = new byte[bytes1.length+bytes2.length];
        System.arraycopy(bytes1, 0, bytes, 0, bytes1.length);
        System.arraycopy(bytes2, 0, bytes, bytes1.length, bytes2.length);
        byte[] encrypt = rsa.encrypt(bytes, KeyType.PrivateKey);
        String encryptStr = Base64Encoder.encode(encrypt);
        template.header(USERNAME_HEADER, encryptStr);
        log.info("register feign interceptor header:{}", template);
    }
}
