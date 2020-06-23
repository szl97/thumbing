package com.loserclub.pushdata.nodeserver.handlers.both;


import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.handlers.PingHandler;
import com.loserclub.pushdata.nodeserver.handlers.data.IDeviceDataHandler;
import com.loserclub.pushdata.nodeserver.handlers.sync.ISyncClientHandler;
import com.loserclub.pushdata.nodeserver.messages.Ping;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class PingBothHandler implements ISyncClientHandler<Ping>, IDeviceDataHandler<Ping> {

    @Autowired
    private PingHandler pingHandler;

    @Override
    public boolean support(DefinedMessage<Ping> message) {
        return pingHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, Ping message) throws Exception {
        pingHandler.call(ctx,message);
    }


}
