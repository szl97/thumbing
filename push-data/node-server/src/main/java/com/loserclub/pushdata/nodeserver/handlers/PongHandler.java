package com.loserclub.pushdata.nodeserver.handlers;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.messages.Pong;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Data
public class PongHandler implements INodeToCenterHandler<Pong> {
    @Override
    public boolean support(DefinedMessage<Pong> message) {
        return message instanceof Pong;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Pong message) {
        Channel channel = ctx.channel();
        log.debug("node server receive heart beat response,channel:{}", channel);
    }
}
