package com.loserclub.pushdata.nodeserver.handlers.data;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.handlers.PushDataHandler;
import com.loserclub.pushdata.nodeserver.messages.PushData;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 这里也没用，复制DataCenter的先放到这里吧~ 因为node server到data center都是主动发送的
 */

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class DataFlowToCenterHandler implements IDeviceDataHandler<PushData> {

    @Autowired
    private PushDataHandler pushDataHandler;

    @Override
    public boolean support(DefinedMessage<PushData> message) {
        return pushDataHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushData message) throws Exception {
        pushDataHandler.call(ctx,message);
    }
}
