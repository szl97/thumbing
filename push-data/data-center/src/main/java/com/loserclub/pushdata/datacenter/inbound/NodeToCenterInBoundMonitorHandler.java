package com.loserclub.pushdata.datacenter.inbound;


import com.loserclub.pushdata.datacenter.handlers.monitors.IMonitorsHandler;
import com.loserclub.pushdata.datacenter.messages.NodeMessage;
import com.loserclub.pushdata.datacenter.messages.Ping;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
@Component
public class NodeToCenterInBoundMonitorHandler extends SimpleChannelInboundHandler<String>{

    @Autowired
    private List<IMonitorsHandler> monitorsHandlers;

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
        log.debug("channel inactive, channel:{}", ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
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
