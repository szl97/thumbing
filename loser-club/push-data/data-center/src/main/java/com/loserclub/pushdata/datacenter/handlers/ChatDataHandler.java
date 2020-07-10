package com.loserclub.pushdata.datacenter.handlers;

import com.loserclub.pushdata.common.channel.IChannelManager;
import com.loserclub.pushdata.common.message.ChatData;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.datacenter.device.DeviceManager;
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
 * @date 2020-06-21
 */
@Slf4j
@Component
@Data
public class ChatDataHandler implements com.loserclub.pushdata.common.handlers.IMessageHandler<ChatData> {

    @Autowired
    IChannelManager channelManager;

    @Autowired
    DeviceManager deviceManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof ChatData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, ChatData message) throws Exception {
        Channel channel = ctx.channel();
        HashMap<String, List<Long>> map = new HashMap<>();
        message.getDeviceIds().forEach(
                a -> {
                    String name = deviceManager.getNodeServer(a);
                    if (name != null) {
                        if (!map.containsKey(name)) {
                            map.put(name, new ArrayList<>());
                        }
                        map.get(name).add(a);
                    }
                    //to do 如果要发送到的client还没有建立连接，那么发送到消息队列
                }
        );
        map.entrySet().forEach(
                e -> {
                    Channel writeChannel = channelManager.getChannel(e.getKey());
                    if (writeChannel != null) {
                        try {
                            writeChannel.writeAndFlush(
                                    ChatData.builder()
                                            .name(e.getKey())
                                            .data(message.getData())
                                            .deviceIds(e.getValue())
                                            .fromUser(message.getFromUser())
                                            .build().encode()
                            );
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    //to do 如果要发送到的client还没有建立连接，那么发送到消息队列
                }
        );
        //channel.writeAndFlush(Confirm.builder().build().encode());
        log.debug("Data center receive push data request,channel:{}", channel);
    }
}
