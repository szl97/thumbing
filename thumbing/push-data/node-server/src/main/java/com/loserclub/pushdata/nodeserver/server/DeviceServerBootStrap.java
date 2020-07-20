package com.loserclub.pushdata.nodeserver.server;


import com.loserclub.pushdata.common.server.BaseServerBootStrap;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
import com.loserclub.pushdata.nodeserver.inbound.DeviceToNodeInBoundHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 作为服务端接收客户端设备的请求
 *
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
@NoArgsConstructor
public class DeviceServerBootStrap extends BaseServerBootStrap<DeviceToNodeInBoundHandler, NodeServerConfig> {

    @Override
    protected int getPort() {
        return getAppConfig().getDevicePort();
    }

    @Override
    protected void success() {
        log.info("Node server DeviceServerBootStrap successful! listening port: {}", getAppConfig().getPort());
    }
}
