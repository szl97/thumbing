package com.thumbing.pushdata.nodeserver.handlers.device;

import com.thumbing.pushdata.common.cache.DeviceCache;
import com.thumbing.pushdata.common.constants.AttributeEnum;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.HandShake;
import com.thumbing.pushdata.nodeserver.channel.DeviceDataChannelManager;
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
    private DeviceCache cache;

    @Autowired
    private NodeServerConfig nodeServerConfig;

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
        cache.add(nodeServerConfig.getName(), message.getUserId());
        log.debug("node server save client");
    }
}
