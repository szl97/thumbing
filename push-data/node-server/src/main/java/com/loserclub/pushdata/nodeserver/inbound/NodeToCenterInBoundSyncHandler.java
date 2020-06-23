package com.loserclub.pushdata.nodeserver.inbound;


import com.loserclub.pushdata.nodeserver.handlers.data.InActiveHandlerForDataFlow;
import com.loserclub.pushdata.nodeserver.handlers.sync.ISyncClientHandler;
import com.loserclub.pushdata.nodeserver.messages.NodeMessage;
import com.loserclub.pushdata.nodeserver.messages.Ping;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NodeToCenterInBoundSyncHandler extends SimpleChannelInboundHandler<String>{

    @Autowired
    private List<ISyncClientHandler> monitorsHandlers;

    @Autowired
    private InActiveHandlerForDataFlow inActiveHandler;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        NodeMessage nodeMessage = NodeMessage.decode(message);

        if (!monitorsHandlers.isEmpty()) {
            monitorsHandlers.stream().forEach((handler) -> {
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel active, channel:{}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        inActiveHandler.call(ctx,null);
        log.debug("channel inactive, channel:{}", ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        inActiveHandler.call(ctx,null);
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
