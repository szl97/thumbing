package com.thumbing.pushdata.datacenter.server;

import com.thumbing.pushdata.common.server.BaseServerBootStrap;
import com.thumbing.pushdata.datacenter.config.DataCenterConfig;
import com.thumbing.pushdata.datacenter.inbound.NodeToCenterInBoundMonitorHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Slf4j
@Component
@Data
public class ServerConnectMonitorBootStrap extends BaseServerBootStrap<NodeToCenterInBoundMonitorHandler, DataCenterConfig> {

    @Override
    protected int getPort() {
        return getAppConfig().getPort();
    }

    @Override
    protected void success() {
        log.info("Data center ServerConnectMonitorBootStrap successful! listening port: {}", getAppConfig().getPort());
    }

}
