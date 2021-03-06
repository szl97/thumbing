package com.thumbing.pushdata.nodeserver.handlers.center;

import com.thumbing.pushdata.common.handlers.IInActiveHandler;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.nodeserver.channel.DataFlowChannelManager;
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
    private DataFlowChannelManager channelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return true;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Object message) {
        Channel channel = ctx.channel();
        channelManager.removeChannel(channel);
        log.debug("Data center to node server inactive,channel:{}", channel);
    }
}
