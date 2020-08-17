package com.thumbing.pushdata.nodeserver.handlers.center;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.PushData;
import com.thumbing.pushdata.nodeserver.channel.DeviceDataChannelManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 18:54
 */
@Slf4j
@Component
@Data
public class PushDataFromCenterHandler implements ICenterDataHandler<PushData> {
    @Autowired
    DeviceDataChannelManager deviceChannelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof PushData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushData message){
        message.getToUserIds().parallelStream().forEach(
                l->{
                    Channel channel = deviceChannelManager.getChannel(l);
                    if(channel != null){

                        try {
                            channel.writeAndFlush(
                                    PushData.builder()
                                            .pushType(message.getPushType())
                                            .dataId(message.getDataId())
                                            .data(message.getData())
                                            .fromUserId(message.getFromUserId())
                                            .fromUserName(message.getFromUserName())
                                            .fromUserNickName(message.getFromUserNickName())
                                            .build()
                                            .encode()
                            );
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        log.info("node server send push data");
    }
}
