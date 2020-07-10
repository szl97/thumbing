package com.loserclub.pushdata.common.channel;

import com.loserclub.pushdata.common.constants.AttributeEnum;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-06-21
 */
public interface IChannelManager<T> {

    void bindAttributes(T id, Channel channel, List<AttributeEnum> attributeKeys);

    T getNodeOrDeviceId(Channel channel);

    Channel getChannel(T id);

    void removeChannel(Channel channel);

    void removeChannel(T id);
}
