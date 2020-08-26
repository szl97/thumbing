package com.thumbing.pushdata.datacenter.inbound;

import com.thumbing.pushdata.common.handlers.EmptyInActiveHandler;
import com.thumbing.pushdata.common.inbund.MessageInBoundHandler;
import com.thumbing.pushdata.datacenter.handlers.monitors.IMonitorsHandler;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 监控设备客户端连接建立的消息处理类
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NodeToCenterInBoundMonitorHandler extends MessageInBoundHandler<IMonitorsHandler, EmptyInActiveHandler> {

}
