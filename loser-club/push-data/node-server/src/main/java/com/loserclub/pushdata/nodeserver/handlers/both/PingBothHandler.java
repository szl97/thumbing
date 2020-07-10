package com.loserclub.pushdata.nodeserver.handlers.both;



import com.loserclub.pushdata.common.handlers.PingHandler;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.common.message.Ping;
import com.loserclub.pushdata.nodeserver.handlers.center.ICenterDataHandler;
import com.loserclub.pushdata.nodeserver.handlers.device.IDeviceDataHandler;
import com.loserclub.pushdata.nodeserver.handlers.sync.ISyncClientHandler;
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
public class PingBothHandler implements ISyncClientHandler<Ping>, ICenterDataHandler<Ping>, IDeviceDataHandler<Ping> {

    @Autowired
    private PingHandler pingHandler;

    @Override
    public boolean support(DefinedMessage<Ping> message) {
        return pingHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, Ping message) throws Exception {
        pingHandler.call(ctx, message);
        log.debug("Node server receive heart beat request,channel:{}",ctx.channel());
    }
}
