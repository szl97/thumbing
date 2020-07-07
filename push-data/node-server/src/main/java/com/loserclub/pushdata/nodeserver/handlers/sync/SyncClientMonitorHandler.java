package com.loserclub.pushdata.nodeserver.handlers.sync;


import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.handlers.ConnectSetHandler;
import com.loserclub.pushdata.nodeserver.messages.ConnectSet;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 此处应该是成功确认操作，以及失败处理操作，以后补上，需要redis和数据库保存消息的状态
 */

/**
 * @author Stan Sai
 * @date 2020-06-23
 */

@Slf4j
@Component
@Data
public class SyncClientMonitorHandler implements ISyncClientHandler<ConnectSet> {

    @Autowired
    private ConnectSetHandler connectSetHandler;

    @Override
    public boolean support(DefinedMessage<ConnectSet> message) {
        return connectSetHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, ConnectSet message) throws Exception {
       connectSetHandler.call(ctx,message);
    }
}
