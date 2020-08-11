package com.thumbing.pushdata.nodeserver.handlers.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.message.ChatData;
import com.thumbing.pushdata.nodeserver.handlers.ChatDataHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-08-11 22:02
 */

@Slf4j
@Component
@Data
public class ChatDataFromDeviceHandler extends ChatDataHandler implements IDeviceDataHandler<ChatData> {

    @Override
    public void call(ChannelHandlerContext ctx, ChatData message) throws JsonProcessingException {

        //todo: 单聊消息收到后先发送到消息队列，异步写入数据库
        //todo...

        super.call(ctx, message);
    }
}
