package com.loserclub.pushdata.datacenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "push-data.data-center")
@Data
public class DataCenterConfig {
    public int port;
    public int messagePort;
    public String name;
    public int initializedConnect;
}
