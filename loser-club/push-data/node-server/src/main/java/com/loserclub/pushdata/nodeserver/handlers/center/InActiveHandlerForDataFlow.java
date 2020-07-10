package com.loserclub.pushdata.nodeserver.handlers.center;

import com.loserclub.pushdata.common.handlers.IInActiveHandler;
import com.loserclub.pushdata.common.handlers.IMessageHandler;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.channel.DataFlowChannelManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class InActiveHandlerForDataFlow implements IInActiveHandler {

    @Autowired
    DataFlowChannelManager channelManager;

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
