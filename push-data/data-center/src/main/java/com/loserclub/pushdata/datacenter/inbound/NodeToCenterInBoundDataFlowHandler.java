package com.loserclub.pushdata.datacenter.inbound;

import com.loserclub.pushdata.datacenter.handlers.InActiveHandler;
import com.loserclub.pushdata.datacenter.handlers.data.IDeviceDataHandler;
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


/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NodeToCenterInBoundDataFlowHandler extends SimpleChannelInboundHandler<String> {
    @Autowired
    private List<IDeviceDataHandler> dataFlowHandlers;

    @Autowired
    private InActiveHandler inActiveHandler;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        NodeMessage nodeMessage = NodeMessage.decode(message);

        if (!dataFlowHandlers.isEmpty()) {
            dataFlowHandlers.stream().forEach((handler) -> {
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
