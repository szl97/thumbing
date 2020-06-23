package com.loserclub.pushdata.datacenter.handlers.monitors;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.datacenter.handlers.PushReqHandler;
import com.loserclub.pushdata.datacenter.handlers.monitors.IMonitorsHandler;
import com.loserclub.pushdata.datacenter.messages.PushReq;
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
public class SyncClientMonitorHandler implements IMonitorsHandler<PushReq> {

    @Autowired
    private PushReqHandler pushReqHandler;

    @Override
    public boolean support(DefinedMessage<PushReq> message) {
        return pushReqHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushReq message) throws Exception {
        pushReqHandler.call(ctx,message);
    }
}
