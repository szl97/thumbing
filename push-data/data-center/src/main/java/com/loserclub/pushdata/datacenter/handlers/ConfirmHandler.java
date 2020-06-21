package com.loserclub.pushdata.datacenter.handlers;

import com.loserclub.pushdata.common.channel.IChannelManager;
import com.loserclub.pushdata.common.constants.AttributeEnum;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.datacenter.Messages.recvforsync.Confirm;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Data
public class ConfirmHandler implements INodeToCenterHandler<Confirm> {

    @Autowired
    IChannelManager channelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof Confirm;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Confirm message) {
        Channel channel = ctx.channel();
        List<AttributeEnum> attributeEnums = new ArrayList<>();
        attributeEnums.add(AttributeEnum.CHANNEL_ATTR_DATACENTER);
        channelManager.bindAttributes(message.getNodeIpWithPort(), channel, attributeEnums);
    }
}
