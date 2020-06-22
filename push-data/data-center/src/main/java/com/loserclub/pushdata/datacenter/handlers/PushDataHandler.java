package com.loserclub.pushdata.datacenter.handlers;

import com.loserclub.pushdata.common.channel.IChannelManager;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.datacenter.channel.NodeToCenterChannelManager;
import com.loserclub.pushdata.datacenter.device.DeviceManager;
import com.loserclub.pushdata.datacenter.messages.Confirm;
import com.loserclub.pushdata.datacenter.messages.PushData;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class PushDataHandler implements INodeToCenterHandler<PushData> {

    @Autowired
    IChannelManager channelManager;

    @Autowired
    DeviceManager deviceManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof PushData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushData message) throws Exception {
        Channel channel = ctx.channel();
        message.getDeviceIds().forEach(
                a->{
                    String name = deviceManager.getNodeServer(a);
                    if(name != null){
                        Channel writeChannel = channelManager.getChannel(name);
                        if(writeChannel != null){
                            writeChannel.writeAndFlush(message);
                        }
                        //to do 如果要发送到的client还没有建立连接，那么发送到消息队列
                    }
                    //to do 如果要发送到的client还没有建立连接，那么发送到消息队列
                }
        );
        channel.writeAndFlush(Confirm.builder().build().encode());
        log.debug("Data center receive push data request,channel:{}", channel);
    }
}
