package com.thumbing.pushdata.datacenter.handlers.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.channel.IChannelManager;
import com.thumbing.pushdata.common.message.GroupData;
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
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class GroupDataFromNodeHandler implements IDeviceDataHandler<GroupData> {

    @Autowired
    IChannelManager channelManager;

    @Autowired
    DeviceManager deviceManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof GroupData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, GroupData message){
        Channel channel = ctx.channel();
        ConcurrentHashMap<String, List<Long>> map = new ConcurrentHashMap<>();
        message.getToUsers().parallelStream().forEach(
                a -> {
                    String name = deviceManager.getNodeServer(a);
                    if (name != null && !name.equals(message.getName())) {
                        if (!map.containsKey(name)) {
                            map.put(name, new ArrayList<>());
                        }
                        map.get(name).add(a);
                    }
                    //todo 要发送到的client还没有建立连接或已建立但由于延时同步未找到连接，发送给所有的node server
                }
        );
        map.entrySet().parallelStream().forEach(
                e -> {
                    Channel writeChannel = channelManager.getChannel(e.getKey());
                    if (writeChannel != null) {
                        try {
                            writeChannel.writeAndFlush(
                                    GroupData.builder()
                                            .name(e.getKey())
                                            .data(message.getData())
                                            .toUsers(e.getValue())
                                            .fromUser(message.getFromUser())
                                            .sessionId(message.getSessionId())
                                            .time(message.getTime())
                                            .last(false)
                                            .build().encode()
                            );
                        } catch (JsonProcessingException ex) {
                            ex.printStackTrace();
                        }

                    }
                    //todo 要发送到的client还没有建立连接或已建立但由于延时同步未找到连接，发送给所有的node serve
                }
        );
        log.info("Data center receive group data request,channel:{}", channel);
    }
}
