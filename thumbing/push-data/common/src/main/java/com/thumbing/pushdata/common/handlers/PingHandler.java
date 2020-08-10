package com.thumbing.pushdata.common.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.Ping;

import com.thumbing.pushdata.common.message.Pong;
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
public class PingHandler implements IMessageHandler<Ping> {
    @Override
    public boolean support(DefinedMessage<Ping> message) {
        return message instanceof Ping;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Ping message) throws JsonProcessingException {
        Channel channel = ctx.channel();
        channel.writeAndFlush(Pong.builder().build().encode());
    }
}
