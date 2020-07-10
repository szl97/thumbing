package com.loserclub.pushdata.nodeserver.channel;

import com.loserclub.pushdata.common.channel.IChannelManager;
import com.loserclub.pushdata.common.constants.AttributeEnum;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理客户端和node-server之间的消息通道，用于客户端的消息推送
 *
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class DeviceDataChannelManager implements IChannelManager<Long> {
    private Map<Long, Channel> channelPool = new ConcurrentHashMap<>();

    @PreDestroy
    public void destory() {
        channelPool.clear();
    }

    @Override
    public void bindAttributes(Long deviceId, Channel channel, List<AttributeEnum> attributeKeys) {
        channelPool.put(deviceId, channel);

        attributeKeys.forEach(a -> {
            if (a == AttributeEnum.CHANNEL_ATTR_DEVICE) {
                channel.attr(a.getAttributeKey()).set(deviceId);
            }
            if (a == AttributeEnum.CHANNEL_ATTR_HANDSHAKE) {
                channel.attr(a.getAttributeKey()).set(true);
            }
        });
    }

    @Override
    public Long getNodeOrDeviceId(Channel channel) {
        return channel.hasAttr(AttributeEnum.CHANNEL_ATTR_DEVICE.getAttributeKey()) ?
                Long.parseLong(channel.attr(AttributeEnum.CHANNEL_ATTR_DEVICE.getAttributeKey()).get().toString()) :
                null;
    }

    @Override
    public void removeChannel(Channel channel) {
        Long id = getNodeOrDeviceId(channel);
        if (id != null) {
            channelPool.remove(id);
        }
    }

    @Override
    public void removeChannel(Long id) {
        if (channelPool.containsKey(id)) {
            channelPool.remove(id);
        }
    }


    @Override
    public Channel getChannel(Long id) {
        return channelPool.get(id);
    }

//    public List<Channel> getAllChannels(){
//        List<Channel> list = new ArrayList<>();
//        channelPool.entrySet().forEach(e-> {
//                    list.add(e.getValue());
//                }
//        );
//        return list;
//    }

    public List<Long> getAllDevices() {
        List<Long> list = new ArrayList<>();
        channelPool.entrySet().forEach(e -> {
                    list.add(e.getKey());
                }
        );
        return list;
    }
}
