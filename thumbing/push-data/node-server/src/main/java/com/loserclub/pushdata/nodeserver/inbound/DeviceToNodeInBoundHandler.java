package com.loserclub.pushdata.nodeserver.inbound;

import com.loserclub.pushdata.common.inbund.MessageInBoundHandler;
import com.loserclub.pushdata.nodeserver.handlers.device.IDeviceDataHandler;
import com.loserclub.pushdata.nodeserver.handlers.device.InActiveHandlerForDevice;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备客户端消息的处理类
 *
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class DeviceToNodeInBoundHandler extends MessageInBoundHandler<IDeviceDataHandler, InActiveHandlerForDevice> {

}
