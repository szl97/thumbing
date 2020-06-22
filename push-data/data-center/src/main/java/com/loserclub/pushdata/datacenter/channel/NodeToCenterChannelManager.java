package com.loserclub.pushdata.datacenter.channel;

import com.loserclub.pushdata.common.channel.IChannelManager;
import com.loserclub.pushdata.common.constants.AttributeEnum;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Data
public class NodeToCenterChannelManager implements IChannelManager {

    private static final Map<String, Channel> channelPool = new ConcurrentHashMap<>();

    @Override
    public void bindAttributes(String name, Channel channel, List<AttributeEnum> attributeKeys) {
        channelPool.put(name,channel);

        attributeKeys.forEach(a->{
            if(a == AttributeEnum.CHANNEL_ATTR_DATACENTER) {
                channel.attr(a.getAttributeKey()).set(name);
            }
        });
    }

    @Override
    public String getNodeOrDeviceId(Channel channel) {
        return channel.hasAttr(AttributeEnum.CHANNEL_ATTR_DATACENTER.getAttributeKey()) ?
                channel.attr(AttributeEnum.CHANNEL_ATTR_DATACENTER.getAttributeKey()).get().toString():
                null;
    }

    @Override
    public Channel getChannel(String name) {
        return channelPool.get(name);
    }
}
