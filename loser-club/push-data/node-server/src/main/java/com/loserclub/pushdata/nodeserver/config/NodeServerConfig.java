package com.loserclub.pushdata.nodeserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Configuration
@ConfigurationProperties(prefix = "push-data.node-server")
@Data
public class NodeServerConfig {
    private int port;
    private int messagePort;
    private int devicePort;
    private String name;
    private int initializedConnect;
}
