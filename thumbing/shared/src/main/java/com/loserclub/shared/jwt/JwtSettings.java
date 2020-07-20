package com.loserclub.shared.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/13 21:08
 */
@Configuration
@ConfigurationProperties(prefix = "authentication")
@Data
public class JwtSettings {
    private Integer expirationTime;
    /**
     * Token issuer.
     */
    private String issuer;


    private String signingKey;
}
