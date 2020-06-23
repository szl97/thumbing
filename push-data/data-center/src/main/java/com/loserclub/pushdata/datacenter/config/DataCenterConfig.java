package com.loserclub.pushdata.datacenter.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * @author Stan Sai
 * @date 2020-06-20
 */
@Configuration
@ConfigurationProperties(prefix = "push-data.data-center")
@Data
public class DataCenterConfig {
    private int port;
    private int messagePort;
    private String name;
    private int initializedConnect;
}
