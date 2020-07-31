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
import java.util.concurrent.ConcurrentHashMap;


/**
 * 管理data-center和node-server之间的消息通道，用于和客户端建立连接之后告诉data-center此客户端建立连接的node-server是哪个
 *
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class SyncClientChannelManager implements IChannelManager<String> {

    private Map<String, Channel> channelPool = new ConcurrentHashMap<>();


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

    public List<Channel> getAllChannels() {
        List<Channel> list = new ArrayList<>();
        channelPool.entrySet().forEach(e -> {
                    list.add(e.getValue());
                }
        );
        return list;
    }
}