package com.loserclub.pushdata.datacenter.handlers.data;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.datacenter.handlers.ConfirmHandler;
import com.loserclub.pushdata.datacenter.handlers.monitors.IMonitorsHandler;
import com.loserclub.pushdata.datacenter.messages.Confirm;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class ConfirmDataFlowHandler implements IDeviceDataHandler<Confirm> {

    @Autowired
    private ConfirmHandler confirmHandler;

    @Override
    public boolean support(DefinedMessage<Confirm> message) {
        return confirmHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, Confirm message) {
        confirmHandler.call(ctx,message);
    }
}
