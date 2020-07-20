package com.loserclub.pushdata.nodeserver.client;

import com.loserclub.pushdata.common.Infos.BaseAppInfo;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.client.BaseClientBootStrap;
import com.loserclub.pushdata.common.constants.AttributeEnum;
import com.loserclub.pushdata.common.constants.OperationEnum;
import com.loserclub.pushdata.common.message.ConnectSet;
import com.loserclub.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.loserclub.pushdata.nodeserver.channel.SyncClientChannelManager;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
import com.loserclub.pushdata.nodeserver.inbound.NodeToCenterInBoundSyncHandler;
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
                            .deviceIds(devices)
                            .build()
                            .encode()
            );
        }
        log.info("连接data center监控通道成功");
    }

}
