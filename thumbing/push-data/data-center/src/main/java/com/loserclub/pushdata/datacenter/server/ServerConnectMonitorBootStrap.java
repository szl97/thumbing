package com.loserclub.pushdata.datacenter.server;

import com.loserclub.pushdata.common.server.BaseServerBootStrap;
import com.loserclub.pushdata.datacenter.config.DataCenterConfig;
import com.loserclub.pushdata.datacenter.inbound.NodeToCenterInBoundMonitorHandler;
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
