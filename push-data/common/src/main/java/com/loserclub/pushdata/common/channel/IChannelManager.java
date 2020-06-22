package com.loserclub.pushdata.common.channel;

import com.loserclub.pushdata.common.constants.AttributeEnum;
import io.netty.channel.Channel;

import java.util.List;

public interface IChannelManager {

    void bindAttributes(String id, Channel channel, List<AttributeEnum> attributeKeys);

    String getNodeOrDeviceId(Channel channel);

    Channel getChannel(String id);

    void removeChannel(Channel channel);

    void removeChannel(String id);
}
