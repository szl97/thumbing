package com.loserclub.pushdata.nodeserver.handlers;

import com.loserclub.pushdata.common.handlers.IMessageHandler;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.channel.SyncClientChannelManager;
import com.loserclub.pushdata.nodeserver.messages.ConnectSet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;



/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class ConnectSetHandler implements IMessageHandler<ConnectSet> {

    @Autowired
    SyncClientChannelManager syncClientChannelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof ConnectSet;
    }

    @Override
    public void call(ChannelHandlerContext ctx, ConnectSet message) {
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
