package com.thumbing.pushdata.datacenter.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.cache.DeviceCache;
import com.thumbing.pushdata.common.channel.IChannelManager;
import com.thumbing.pushdata.common.handlers.IMessageHandler;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.PushData;
import com.thumbing.pushdata.datacenter.monitors.CenterZkMonitor;
import com.thumbing.pushdata.datacenter.utils.NodeServerUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 18:26
 */
@Slf4j
@Component
@Data
public class PushDataHandler implements IMessageHandler<PushData> {

    @Autowired
    private IChannelManager channelManager;

    @Autowired
    private CenterZkMonitor zkMonitor;

    @Autowired
    private DeviceCache cache;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof PushData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushData message) {
        Set<String> nodes = zkMonitor.getAllNodes();
        HashMap<String, List<Long>> map = new HashMap<>();
        message.getToUserIds().forEach(
                a -> {
                    String name = NodeServerUtils.getNodeServer(cache, nodes, a);
                    if (name != null) {
                        if (!map.containsKey(name)) {
                            map.put(name, new ArrayList<>());
                        }
                        map.get(name).add(a);
                    }
                }
        );
        map.entrySet().parallelStream().forEach(
                e -> {
                    Channel writeChannel = channelManager.getChannel(e.getKey());
                    if (writeChannel != null) {
                        try {
                            writeChannel.writeAndFlush(
                                    PushData.builder().pushType(message.getPushType())
                                            .dataId(message.getDataId())
                                            .data(message.getData())
                                            .toUserIds(e.getValue())
                                            .fromUserId(message.getFromUserId())
                                            .fromUserName(message.getFromUserName())
                                            .fromUserNickName(message.getFromUserNickName())
                                            .time(message.getTime())
                                            .build().encode()
                            );
                        } catch (JsonProcessingException ex) {
                            ex.printStackTrace();
                        }

                    }
                }
        );
        log.info("Data center receive push data request");
    }

}
