package com.loserclub.pushdata.nodeserver.handlers.device;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.loserclub.pushdata.nodeserver.messages.HandShake;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class HandShakeHandler implements IServerDataHandler<HandShake> {

    @Autowired
    private DeviceDataChannelManager deviceDataChannelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof HandShake;
    }

    @Override
    public void call(ChannelHandlerContext ctx, HandShake message){
        
    }
}
