package com.loserclub.pushdata.datacenter.inbound;


import com.loserclub.pushdata.common.handlers.EmptyInActiceHandler;
import com.loserclub.pushdata.common.inbund.MessageInBoundHandler;
import com.loserclub.pushdata.datacenter.handlers.monitors.IMonitorsHandler;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 监控设备客户端连接建立的消息处理类
 *
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NodeToCenterInBoundMonitorHandler extends MessageInBoundHandler<IMonitorsHandler, EmptyInActiceHandler> {

}
