package com.thumbing.pushdata.nodeserver.client;

import com.thumbing.pushdata.common.Infos.DataCenterInfo;
import com.thumbing.pushdata.common.client.BaseClientBootStrap;
import com.thumbing.pushdata.common.constants.OperationEnum;
import com.thumbing.pushdata.common.message.ConnectSet;
import com.thumbing.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.thumbing.pushdata.nodeserver.channel.SyncClientChannelManager;
import com.thumbing.pushdata.nodeserver.config.NodeServerConfig;
import com.thumbing.pushdata.nodeserver.inbound.NodeToCenterInBoundSyncHandler;
import io.netty.channel.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 与设备客户端建立连接后，作为消息中心的客户端，将建立连接的消息发送给data-center
 *
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class SyncConnectClientBootStrap extends BaseClientBootStrap<SyncClientChannelManager, NodeServerConfig, NodeToCenterInBoundSyncHandler> {

    @Autowired
    private DeviceDataChannelManager deviceDataChannelManager;

    @Override
    protected int getServerPort(DataCenterInfo info) {
        return info.getPort();
    }

    @Override
    protected void success(Channel channel) throws Exception {
        List<Long> devices = deviceDataChannelManager.getAllDevices();
        if (devices.size() > 0) {
            channel.writeAndFlush(
                    ConnectSet.builder()
                            .name(getAppConfig().getName())
                            .operation(OperationEnum.ADD)
                            .userIds(devices)
                            .build()
                            .encode()
            );
        }
        log.info("连接data center监控通道成功");
    }

}
