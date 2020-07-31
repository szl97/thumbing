package com.thumbing.pushdata.datacenter.handlers;

import com.thumbing.pushdata.common.constants.OperationEnum;
import com.thumbing.pushdata.common.message.Confirm;
import com.thumbing.pushdata.common.message.ConnectSet;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.Fail;
import com.thumbing.pushdata.datacenter.device.DeviceManager;
import com.thumbing.pushdata.common.handlers.IMessageHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Slf4j
@Component
@Data
public class ConnectSetHandler implements IMessageHandler<ConnectSet> {

    @Autowired
    DeviceManager deviceManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof ConnectSet;
    }

    @Override
    public void call(ChannelHandlerContext ctx, ConnectSet message) throws Exception {
        Channel channel = ctx.channel();
        if (deviceManager.isExists(message.getName())) {
            List<Long> ids = message.getDeviceIds();
            if (message.getOperation() == OperationEnum.ADD) {
                ids.forEach(i -> deviceManager.addOrUpdateClient(i, message.getName()));
            } else if (message.getOperation() == OperationEnum.DEL) {
                ids.forEach(i -> deviceManager.removeClient(i, message.getName()));
            }
            channel.writeAndFlush(Confirm.builder().build().encode());
        } else {
            channel.writeAndFlush(Fail.builder().build().encode());
        }
        log.debug("Data center receive sync client request,channel:{}", channel);
    }
}
