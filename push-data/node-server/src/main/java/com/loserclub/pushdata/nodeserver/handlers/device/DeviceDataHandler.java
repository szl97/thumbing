package com.loserclub.pushdata.nodeserver.handlers.device;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
import com.loserclub.pushdata.nodeserver.handlers.PushDataHandler;
import com.loserclub.pushdata.nodeserver.messages.PushData;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class DeviceDataHandler implements IServerDataHandler<PushData> {

    @Autowired
    private PushDataHandler pushDataHandler;

    @Override
    public boolean support(DefinedMessage message) {
        return pushDataHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushData message) throws Exception {
        pushDataHandler.call(ctx,message);
    }
}
