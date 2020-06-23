package com.loserclub.pushdata.nodeserver.handlers.sync;


import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.handlers.PushReqHandler;
import com.loserclub.pushdata.nodeserver.messages.PushReq;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 此处应该是成功确认操作，以及失败处理操作，以后补上，需要redis和数据库保存消息的状态
 */
@Slf4j
@Component
@Data
public class SyncClientMonitorHandler implements ISyncClientHandler<PushReq> {

    @Autowired
    private PushReqHandler pushReqHandler;

    @Override
    public boolean support(DefinedMessage<PushReq> message) {
        return pushReqHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushReq message) throws Exception {
        pushReqHandler.call(ctx,message);
    }
}
