package com.thumbing.pushdata.datacenter.handlers.monitors;

import com.thumbing.pushdata.common.message.ConnectSet;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.datacenter.handlers.ConnectSetHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class SyncClientMonitorHandler implements IMonitorsHandler<ConnectSet> {

    @Autowired
    private ConnectSetHandler connectSetHandler;

    @Override
    public boolean support(DefinedMessage<ConnectSet> message) {
        return connectSetHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, ConnectSet message) throws Exception {
        connectSetHandler.call(ctx, message);
    }
}
