package com.loserclub.pushdata.datacenter.handlers.both;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.datacenter.handlers.PingHandler;
import com.loserclub.pushdata.datacenter.handlers.data.IDeviceDataHandler;
import com.loserclub.pushdata.datacenter.handlers.monitors.IMonitorsHandler;
import com.loserclub.pushdata.datacenter.messages.Ping;
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
public class PingBothHandler implements IMonitorsHandler<Ping>, IDeviceDataHandler<Ping> {

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
