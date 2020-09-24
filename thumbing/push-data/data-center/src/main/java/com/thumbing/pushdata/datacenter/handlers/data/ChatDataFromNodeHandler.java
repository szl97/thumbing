package com.thumbing.pushdata.datacenter.handlers.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.cache.DeviceCache;
import com.thumbing.pushdata.common.channel.IChannelManager;
import com.thumbing.pushdata.common.message.ChatData;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.datacenter.monitors.CenterZkMonitor;
import com.thumbing.pushdata.datacenter.utils.NodeServerUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-08-11 22:43
 */
@Slf4j
@Component
@Data
public class ChatDataFromNodeHandler implements IDeviceDataHandler<ChatData> {

    @Autowired
    private IChannelManager channelManager;

    @Autowired
    private CenterZkMonitor zkMonitor;

    @Autowired
    private DeviceCache cache;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof  ChatData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, ChatData message) throws JsonProcessingException {
        Set<String> nodes = zkMonitor.getAllNodes();
        String name = NodeServerUtils.getNodeServer(cache, nodes, message.getToUser());
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
