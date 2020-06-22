package com.loserclub.pushdata.nodeserver.handlers;

import com.loserclub.pushdata.common.constants.OperationEnum;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.channel.SyncClientChannelManager;
import com.loserclub.pushdata.nodeserver.messages.PushReq;
import io.netty.channel.Channel;
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
    SyncClientChannelManager syncClientChannelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof PushReq;
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushReq message) {
        List<Channel> channels = syncClientChannelManager.getAllChannels();

        channels.forEach(
                c-> {
                    try {
                        c.writeAndFlush(message.encode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        log.debug("node server send sync client request");
    }
}
