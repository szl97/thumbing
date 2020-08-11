package com.thumbing.pushdata.common.handlers;


import com.thumbing.pushdata.common.message.DefinedMessage;
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
@Data
public abstract class PongHandler implements IMessageHandler<Pong> {
    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof Pong;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Pong message) {
        Channel channel = ctx.channel();
        log.info("Receive heart beat response,channel:{}", ctx.channel());
    }
}
