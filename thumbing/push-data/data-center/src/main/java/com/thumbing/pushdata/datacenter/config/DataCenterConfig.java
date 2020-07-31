package com.thumbing.pushdata.datacenter.config;

import com.thumbing.pushdata.common.config.BaseAppConfig;
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
public class DataCenterConfig extends BaseAppConfig {
}
