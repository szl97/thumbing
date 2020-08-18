package com.thumbing.pushdata.datacenter.handlers;

import com.thumbing.pushdata.common.channel.IChannelManager;
import com.thumbing.pushdata.common.handlers.IInActiveHandler;
import com.thumbing.pushdata.common.message.DefinedMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class InActiveHandler implements IInActiveHandler {

    @Autowired
    private IChannelManager channelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return true;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Object message){
        Channel channel = ctx.channel();
        channelManager.removeChannel(channel);
        log.debug("Data center to node server inactive,channel:{}", channel);
    }
}
