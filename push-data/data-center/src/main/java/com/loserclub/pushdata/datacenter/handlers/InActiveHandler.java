package com.loserclub.pushdata.datacenter.handlers;

import com.loserclub.pushdata.common.channel.IChannelManager;
import com.loserclub.pushdata.common.message.DefinedMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Data
public class InActiveHandler implements INodeToCenterHandler {

    @Autowired
    IChannelManager channelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return true;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Object message) throws Exception {
        Channel channel = ctx.channel();
        channelManager.removeChannel(channel);
        log.debug("Data center to node server inactive,channel:{}", channel);
    }
}
