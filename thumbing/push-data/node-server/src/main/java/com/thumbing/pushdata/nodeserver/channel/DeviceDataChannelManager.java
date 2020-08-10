package com.thumbing.pushdata.nodeserver.channel;

import com.thumbing.pushdata.common.channel.IChannelManager;
import com.thumbing.pushdata.common.constants.AttributeEnum;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

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
    private Map<Long, Channel> channelPool = new ConcurrentSkipListMap<>();

    @PreDestroy
    public void destory() {
        channelPool.clear();
    }

    @Override
    public void bindAttributes(Long userId, Channel channel, List<AttributeEnum> attributeKeys) {
        channelPool.put(userId, channel);

        attributeKeys.forEach(a -> {
            if (a == AttributeEnum.CHANNEL_ATTR_DEVICE) {
                channel.attr(a.getAttributeKey()).set(userId);
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


    public List<Long> getAllDevices() {
        List<Long> list = new ArrayList<>();
        channelPool.entrySet().forEach(e -> {
                    list.add(e.getKey());
                }
        );
        return list;
    }
}
