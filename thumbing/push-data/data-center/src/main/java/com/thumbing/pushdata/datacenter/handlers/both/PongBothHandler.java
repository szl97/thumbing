package com.thumbing.pushdata.datacenter.handlers.both;

import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.Pong;
import com.thumbing.pushdata.common.handlers.PongHandler;
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
public class PongBothHandler implements IMonitorsHandler<Pong>, IDeviceDataHandler<Pong> {

    @Autowired
    private PongHandler pongHandler;

    @Override
    public boolean support(DefinedMessage<Pong> message) {
        return pongHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, Pong message) {
        pongHandler.call(ctx, message);
        log.info("Data center receive heart beat response,channel:{}", ctx.channel());
    }
}
