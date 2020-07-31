package com.thumbing.pushdata.datacenter.handlers.data;

import com.thumbing.pushdata.common.message.Confirm;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.datacenter.handlers.ConfirmHandler;
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
    ConfirmHandler confirmHandler;

    @Override
    public boolean support(DefinedMessage<Confirm> message) {
        return message instanceof Confirm;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Confirm message) {
        confirmHandler.call(ctx, message);
        log.info("Data center received data flow setting confirmation");
    }
}
