package com.thumbing.pushdata.datacenter.channel;

import com.thumbing.pushdata.common.channel.IChannelManager;
import com.thumbing.pushdata.common.constants.AttributeEnum;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;


/**
 * 管理data-center和node-server之间的消息通道，用于客户端的消息推送
 *
 * @author Stan Sai
 * @date 2020-06-21
 */

@Slf4j
@Component
@Data
public class NodeToCenterChannelManager implements IChannelManager<String> {

    private Map<String, Channel> channelPool = new ConcurrentSkipListMap<>();

    @PreDestroy
    public void destory() {
        channelPool.clear();
    }

    @Override
    public void bindAttributes(String name, Channel channel, List<AttributeEnum> attributeKeys) {
        channelPool.put(name, channel);

        attributeKeys.forEach(a -> {
            if (a == AttributeEnum.CHANNEL_ATTR_DATACENTER) {
                channel.attr(a.getAttributeKey()).set(name);
            }
        });
    }

    @Override
    public String getNodeOrDeviceId(Channel channel) {
        return channel.hasAttr(AttributeEnum.CHANNEL_ATTR_DATACENTER.getAttributeKey()) ?
                channel.attr(AttributeEnum.CHANNEL_ATTR_DATACENTER.getAttributeKey()).get().toString() :
                null;
    }

    @Override
    public void removeChannel(Channel channel) {
        String name = getNodeOrDeviceId(channel);
        if (name != null) {
            channelPool.remove(name);
        }
    }

    @Override
    public void removeChannel(String name) {
        if (channelPool.containsKey(name)) {
            channelPool.remove(name);
        }
    }


    @Override
    public Channel getChannel(String name) {
        return channelPool.get(name);
    }
}
