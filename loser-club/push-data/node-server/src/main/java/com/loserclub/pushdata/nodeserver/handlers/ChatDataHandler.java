package com.loserclub.pushdata.nodeserver.handlers;

import com.loserclub.pushdata.common.handlers.IMessageHandler;
import com.loserclub.pushdata.common.message.ChatData;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.channel.DataFlowChannelManager;
import com.loserclub.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
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
public class ChatDataHandler implements IMessageHandler<ChatData> {


    @Autowired
    DataFlowChannelManager dataFlowChannelManager;

    @Autowired
    NodeServerConfig nodeServerConfig;

    @Autowired
    DeviceDataChannelManager deviceChannelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof ChatData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, ChatData message) throws Exception {
        List<Long> others = new ArrayList<>();
        message.getDeviceIds().forEach(id -> {

            Channel channel = deviceChannelManager.getChannel(id);
            if (channel != null) {
                try {
                    channel.writeAndFlush(message.encode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                others.add(id);
            }

        });
        Channel channel = dataFlowChannelManager.getRandomChannel();
        channel.writeAndFlush(ChatData
                .builder()
                .name(nodeServerConfig.getName())
                .data(message.getData())
                .fromUser(message.getFromUser())
                .deviceIds(others)
        );
        log.debug("node server send push data");
    }
}
