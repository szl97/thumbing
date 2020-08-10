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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class ChatDataToNodeHandler implements IDeviceDataHandler<ChatData> {

    @Autowired
    IChannelManager channelManager;

    @Autowired
    DeviceManager deviceManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof ChatData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, ChatData message){
        Channel channel = ctx.channel();
        HashMap<String, List<Long>> map = new HashMap<>();
        message.getToUsers().forEach(
                a -> {
                    String name = deviceManager.getNodeServer(a);
                    if (name != null && !name.equals(message.getName())) {
                        if (!map.containsKey(name)) {
                            map.put(name, new ArrayList<>());
                        }
                        map.get(name).add(a);
                    }
                    //todo 要发送到的client还没有建立连接
                }
        );
        map.entrySet().parallelStream().forEach(
                e -> {
                    Channel writeChannel = channelManager.getChannel(e.getKey());
                    if (writeChannel != null) {
                        try {
                            writeChannel.writeAndFlush(
                                    ChatData.builder()
                                            .name(e.getKey())
                                            .data(message.getData())
                                            .toUsers(e.getValue())
                                            .fromUser(message.getFromUser())
                                            .sessionId(message.getSessionId())
                                            .build().encode()
                            );
                        } catch (JsonProcessingException ex) {
                            ex.printStackTrace();
                        }

                    }
                    //todo 要发送到的client还没有建立连接
                }
        );
        log.info("Data center receive chat data request,channel:{}", channel);
    }
}
