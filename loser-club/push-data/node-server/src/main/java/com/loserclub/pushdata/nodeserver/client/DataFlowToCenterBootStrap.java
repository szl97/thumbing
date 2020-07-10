package com.loserclub.pushdata.nodeserver.client;

import com.loserclub.pushdata.common.Infos.BaseAppInfo;
import com.loserclub.pushdata.common.client.BaseClientBootStrap;
import com.loserclub.pushdata.common.message.Confirm;
import com.loserclub.shared.utils.ip.*;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.constants.AttributeEnum;
import com.loserclub.pushdata.nodeserver.channel.DataFlowChannelManager;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
import com.loserclub.pushdata.nodeserver.inbound.NodeToCenterInBoundDataFlowHandler;
import com.loserclub.pushdata.nodeserver.server.DeviceServerBootStrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


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
