package com.thumbing.pushdata.nodeserver.handlers.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.constants.OperationEnum;
import com.thumbing.pushdata.common.handlers.IInActiveHandler;
import com.thumbing.pushdata.common.message.ConnectSet;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.thumbing.pushdata.nodeserver.channel.SyncClientChannelManager;
import com.thumbing.pushdata.nodeserver.config.NodeServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class InActiveHandlerForDevice implements IInActiveHandler {

    @Autowired
    private DeviceDataChannelManager channelManager;

    @Autowired
    private SyncClientChannelManager syncClientChannelManager;

    @Autowired
    private DeviceDataChannelManager deviceDataChannelManager;

    @Autowired
    private NodeServerConfig nodeServerConfig;

    @Override
    public boolean support(DefinedMessage message) {
        return true;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Object message) {
        Channel channel = ctx.channel();
        channelManager.removeChannel(channel);
        List<Channel> channels = syncClientChannelManager.getAllChannels();
        List<Long> devices = new ArrayList<>();
        devices.add(deviceDataChannelManager.getNodeOrDeviceId(channel));
        channels.forEach(
                c-> {
                    try {
                        c.writeAndFlush(ConnectSet.builder().name(nodeServerConfig.getName())
                                .operation(OperationEnum.DEL)
                                .userIds(devices)
                                .build()
                                .encode());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
        );
        log.debug("Device to node server inactive,channel:{}", channel);
    }
}
