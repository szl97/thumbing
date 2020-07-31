package com.thumbing.pushdata.nodeserver.client;

import com.thumbing.pushdata.common.client.BaseClientBootStrap;
import com.thumbing.pushdata.common.message.Confirm;
import com.thumbing.pushdata.common.Infos.DataCenterInfo;
import com.thumbing.pushdata.nodeserver.channel.DataFlowChannelManager;
import com.thumbing.pushdata.nodeserver.config.NodeServerConfig;
import com.thumbing.pushdata.nodeserver.inbound.NodeToCenterInBoundDataFlowHandler;
import com.thumbing.pushdata.nodeserver.server.DeviceServerBootStrap;
import com.thumbing.shared.utils.ip.IpUtils;
import io.netty.channel.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 作为消息中心的客户端将设备客户端的消息发送给data-center
 *
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class DataFlowToCenterBootStrap extends BaseClientBootStrap<DataFlowChannelManager, NodeServerConfig, NodeToCenterInBoundDataFlowHandler> {

    @Autowired
    DeviceServerBootStrap deviceServerBootStrap;


    @Override
    protected int getServerPort(DataCenterInfo info) {
        return info.getMessagePort();
    }

    @Override
    protected void success(Channel channel) throws Exception {
        String ip = IpUtils.getIp();
        channel.writeAndFlush(
                Confirm.builder()
                        .name(getAppConfig().getName())
                        .nodeIpWithPort(ip + ":" + getAppConfig().getMessagePort())
                        .build().encode()
        );
        deviceServerBootStrap.init();
        log.info("连接data center消息通道成功");
    }
}
