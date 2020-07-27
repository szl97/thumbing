package com.loserclub.pushdata.nodeserver.inbound;


import com.loserclub.pushdata.common.inbund.MessageInBoundHandler;
import com.loserclub.pushdata.nodeserver.handlers.center.InActiveHandlerForDataFlow;
import com.loserclub.pushdata.nodeserver.handlers.sync.ISyncClientHandler;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



/**
 * 消息中心发送设备信息同步回复的处理类
 *
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NodeToCenterInBoundSyncHandler extends MessageInBoundHandler<ISyncClientHandler, InActiveHandlerForDataFlow> {

}