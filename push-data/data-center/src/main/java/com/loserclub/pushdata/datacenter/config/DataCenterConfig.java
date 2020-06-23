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
    public int port;
    public int messagePort;
    public String name;
    public int initializedConnect;
}
