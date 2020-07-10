package com.loserclub.pushdata.common.handlers;


import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.common.message.Pong;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Slf4j
@Component
@Data
public class PongHandler implements IMessageHandler<Pong> {
    @Override
    public boolean support(DefinedMessage<Pong> message) {
        return message instanceof Pong;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Pong message) {
        Channel channel = ctx.channel();
    }
}
