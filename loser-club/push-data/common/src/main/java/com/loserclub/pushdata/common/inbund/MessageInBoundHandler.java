package com.loserclub.pushdata.common.inbund;

import com.loserclub.pushdata.common.handlers.IInActiveHandler;
import com.loserclub.pushdata.common.handlers.IMessageHandler;
import com.loserclub.pushdata.common.message.NodeMessage;
import com.loserclub.pushdata.common.message.Ping;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-07-10
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageInBoundHandler<T extends IMessageHandler, K extends IInActiveHandler> extends SimpleChannelInboundHandler<String> {
    @Autowired
    protected List<T> messageHandlers;
    @Autowired
    private K inActiveHandler;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        NodeMessage nodeMessage = NodeMessage.decode(message);

        if (!messageHandlers.isEmpty()) {
            messageHandlers.stream().forEach((handler) -> {
                try {
                    if (handler.support(nodeMessage)) {
                        handler.call(channelHandlerContext, nodeMessage);
                    }
                } catch (Exception e) {
                    log.error("exception error:{}", e);
                }
            });
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        log.debug("channel registered, channel:{}", ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        log.debug("channel unRegistered, channel:{}", ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.debug("channel active, channel:{}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        inActiveHandler.call(ctx, null);
        log.debug("channel inactive, channel:{}", ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.debug("exception error:{}, channel:{}", cause.getMessage(), ctx.channel());
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                ctx.writeAndFlush(Ping.builder().build().encode());
            }
            if (event.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(Ping.builder().build().encode());
            }
            if (event.state() == IdleState.ALL_IDLE) {
                ctx.writeAndFlush(Ping.builder().build().encode());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
