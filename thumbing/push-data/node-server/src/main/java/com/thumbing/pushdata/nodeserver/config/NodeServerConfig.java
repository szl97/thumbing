package com.thumbing.pushdata.nodeserver.config;

import com.thumbing.pushdata.common.config.BaseAppConfig;
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
public class NodeServerConfig extends BaseAppConfig {

}
