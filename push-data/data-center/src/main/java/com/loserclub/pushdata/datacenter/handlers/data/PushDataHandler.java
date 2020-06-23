package com.loserclub.pushdata.datacenter.handlers.data;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.datacenter.handlers.ConfirmHandler;
import com.loserclub.pushdata.datacenter.handlers.monitors.IMonitorsHandler;
import com.loserclub.pushdata.datacenter.messages.Confirm;
import com.loserclub.pushdata.datacenter.messages.PushData;
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
public class PushDataHandler implements IDeviceDataHandler<Confirm> {

    @Autowired
    private PushDataHandler pushDataHandler;

    @Override
    public boolean support(DefinedMessage<Confirm> message) {
        return pushDataHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, Confirm message) {
        pushDataHandler.call(ctx,message);
    }
}
