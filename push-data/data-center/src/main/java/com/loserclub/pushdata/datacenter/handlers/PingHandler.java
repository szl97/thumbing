package com.loserclub.pushdata.datacenter.handlers;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.datacenter.messages.Ping;
import com.loserclub.pushdata.datacenter.messages.Pong;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class PingHandler implements INodeToCenterHandler<Ping> {
    @Override
    public boolean support(DefinedMessage<Ping> message) {
        return message instanceof Ping;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Ping message) throws Exception {
        Channel channel = ctx.channel();
        channel.writeAndFlush(Pong.builder().build().encode());
        log.debug("Data center receive heart beat request,channel:{}", channel);
    }
}
