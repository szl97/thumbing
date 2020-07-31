package com.thumbing.pushdata.common.config;

import lombok.Data;
/**
 * @author Stan Sai
 * @date 2020-07-10
 */
@Data
public class BaseZookeeperConfig {
    private String servers;

    private String namespace;

    private int sessionTimeout;

    private int connectionTimeout;

    private int maxRetries;

    private int retriesSleepTime;
}
