package com.thumbing.pushdata.nodeserver.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.handlers.IMessageHandler;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.GroupData;
import com.thumbing.pushdata.nodeserver.channel.DataFlowChannelManager;
import com.thumbing.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.thumbing.pushdata.nodeserver.config.NodeServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Data
public abstract class GroupDataHandler implements IMessageHandler<GroupData> {

    @Autowired
    DataFlowChannelManager dataFlowChannelManager;

    @Autowired
    NodeServerConfig nodeServerConfig;

    @Autowired
    DeviceDataChannelManager deviceChannelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof GroupData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, GroupData message) throws JsonProcessingException {
        List<Long> others = new ArrayList<>();
        message.getToUsers().forEach(id -> {
            Channel channel = deviceChannelManager.getChannel(id);
            if (channel != null) {
                try {
                    channel.writeAndFlush(message.encode());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                others.add(id);
            }

        });
        if(!message.isLast()) {
            Channel channel = dataFlowChannelManager.getRandomChannel();
            channel.writeAndFlush(GroupData
                    .builder()
                    .name(nodeServerConfig.getName())
                    .data(message.getData())
                    .fromUser(message.getFromUser())
                    .fromUserNickName(message.getFromUserNickName())
                    .toUsers(others)
                    .sessionId(message.getSessionId())
                    .last(false)
                    .build().encode()
            );
        }
        log.info("node server receive group data");
    }
}
