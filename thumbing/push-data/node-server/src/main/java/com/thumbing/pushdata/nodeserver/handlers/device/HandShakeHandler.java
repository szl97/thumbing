package com.thumbing.pushdata.nodeserver.handlers.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.constants.AttributeEnum;
import com.thumbing.pushdata.common.constants.OperationEnum;
import com.thumbing.pushdata.common.message.ConnectSet;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.HandShake;
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
public class HandShakeHandler implements IDeviceDataHandler<HandShake> {

    @Autowired
    private DeviceDataChannelManager channelManager;

    @Autowired
    SyncClientChannelManager syncClientChannelManager;

    @Autowired
    NodeServerConfig nodeServerConfig;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof HandShake;
    }

    @Override
    public void call(ChannelHandlerContext ctx, HandShake message) {
        Channel channel = ctx.channel();
        List<AttributeEnum> attributeEnums = new ArrayList<>();
        attributeEnums.add(AttributeEnum.CHANNEL_ATTR_DEVICE);
        attributeEnums.add(AttributeEnum.CHANNEL_ATTR_HANDSHAKE);
        channelManager.bindAttributes(message.getUserId(), channel, attributeEnums);
        log.debug("Device connect with node server,channel:{}", channel);
        List<Channel> channels = syncClientChannelManager.getAllChannels();
        List<Long> devices = new ArrayList<>();
        devices.add(message.getUserId());
        channels.forEach(
                c-> {
                    try {
                        c.writeAndFlush(ConnectSet.builder().name(nodeServerConfig.getName())
                                .operation(OperationEnum.ADD)
                                .userIds(devices)
                                .build()
                                .encode());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                }
        );
        log.debug("node server send sync client request");
    }
}
