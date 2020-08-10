package com.thumbing.pushdata.datacenter.handlers.both;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.Ping;
import com.thumbing.pushdata.common.handlers.PingHandler;

import com.thumbing.pushdata.datacenter.handlers.data.IDeviceDataHandler;
import com.thumbing.pushdata.datacenter.handlers.monitors.IMonitorsHandler;
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
    public void call(ChannelHandlerContext ctx, Ping message) throws JsonProcessingException {
        pingHandler.call(ctx, message);
        log.debug("Data center receive heart beat request,channel:{}",ctx.channel());
    }


}
