package com.loserclub.pushdata.nodeserver.handlers.both;


import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.handlers.PongHandler;
import com.loserclub.pushdata.nodeserver.handlers.center.ICenterDataHandler;
import com.loserclub.pushdata.nodeserver.handlers.device.IDeviceDataHandler;
import com.loserclub.pushdata.nodeserver.handlers.sync.ISyncClientHandler;
import com.loserclub.pushdata.nodeserver.messages.Pong;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class PongBothHandler implements ISyncClientHandler<Pong>, ICenterDataHandler<Pong>, IDeviceDataHandler<Pong> {

    @Autowired
    private PongHandler pongHandler;

    @Override
    public boolean support(DefinedMessage<Pong> message) {
        return pongHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, Pong message) {
        pongHandler.call(ctx,message);
    }
}
