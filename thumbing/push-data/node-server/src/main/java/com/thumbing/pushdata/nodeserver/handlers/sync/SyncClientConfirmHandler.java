package com.thumbing.pushdata.nodeserver.handlers.sync;

import com.thumbing.pushdata.common.message.Confirm;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.nodeserver.channel.DataFlowChannelManager;
import com.thumbing.pushdata.nodeserver.handlers.center.ICenterDataHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 收到确认消息说明同步成功
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class SyncClientConfirmHandler implements ISyncClientHandler<Confirm> {

    @Autowired
    DataFlowChannelManager channelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof Confirm;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Confirm message) {
        Channel channel = ctx.channel();
        log.debug("Node Server receive sync client confirm,channel:{}", channel);
    }
}
