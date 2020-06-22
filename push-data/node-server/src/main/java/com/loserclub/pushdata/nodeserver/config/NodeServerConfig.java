package com.loserclub.pushdata.nodeserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "push-data.node-server")
@Data
public class NodeServerConfig {
    public int port;
    public int messagePort;
    public String name;
    public int initializedConnect;
}
