package com.loserclub.pushdata.datacenter.inbound;

import com.loserclub.pushdata.common.inbund.MessageInBoundHandler;
import com.loserclub.pushdata.datacenter.handlers.InActiveHandler;
import com.loserclub.pushdata.datacenter.handlers.data.IDeviceDataHandler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



/**
 * 用于设备客户端消息传输的连接的消息处理类
 *
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NodeToCenterInBoundDataFlowHandler extends MessageInBoundHandler<IDeviceDataHandler, InActiveHandler> {

}
