package com.thumbing.pushdata.common.config;

import lombok.Data;
/**
 * @author Stan Sai
 * @date 2020-07-10
 */
@Data
public class BaseAppConfig {
    private int port;
    private int messagePort;
    private int devicePort;
    private String name;
    private int initializedConnect;
}
