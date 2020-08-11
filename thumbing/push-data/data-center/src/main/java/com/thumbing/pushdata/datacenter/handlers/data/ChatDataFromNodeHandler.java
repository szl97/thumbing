package com.thumbing.pushdata.datacenter.handlers.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.channel.IChannelManager;
import com.thumbing.pushdata.common.message.ChatData;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.datacenter.device.DeviceManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-08-11 22:43
 */
@Slf4j
@Component
@Data
public class ChatDataFromNodeHandler implements IDeviceDataHandler<ChatData> {

    @Autowired
    IChannelManager channelManager;

    @Autowired
    DeviceManager deviceManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof  ChatData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, ChatData message) throws JsonProcessingException {
        String name = deviceManager.getNodeServer(message.getFromUser());
        Channel channel = channelManager.getChannel(name);
        if(!name.equals(message.getName()) && channel != null) {
            channel.writeAndFlush(message.encode());
        }
        else {
            message.setLast(true);
            List<Channel> channels = channelManager.getAll();
            channels.parallelStream().forEach(
                    c->{
                        try {
                            c.writeAndFlush(message.encode());
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
            );

        }
        log.info("Data center receive group data request,channel:{}", ctx.channel());
    }
}
