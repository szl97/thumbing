package com.loserclub.pushdata.datacenter.handlers;

import com.loserclub.pushdata.common.constants.OperationEnum;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.datacenter.messages.recvforsync.PushReq;
import com.loserclub.pushdata.datacenter.device.DeviceManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Data
public class PushReqHandler implements INodeToCenterHandler<PushReq> {

    @Autowired
    DeviceManager deviceManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof PushReq;
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushReq message) {
        List<String> ids = message.getDeviceIds();
        if(message.getOperation() == OperationEnum.ADD){
            ids.forEach(i->deviceManager.addOrUpdateClient(i,message.getName()));
        }
        else if(message.getOperation() == OperationEnum.DEL){
            ids.forEach(i->deviceManager.removeClient(i,message.getName()));
        }
    }
}
