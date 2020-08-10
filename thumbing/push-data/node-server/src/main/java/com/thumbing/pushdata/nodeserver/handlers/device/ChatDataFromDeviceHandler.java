package com.thumbing.pushdata.nodeserver.handlers.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.message.ChatData;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.nodeserver.handlers.ChatDataHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class ChatDataFromDeviceHandler implements IDeviceDataHandler<ChatData> {

    @Autowired
    private ChatDataHandler chatDataHandler;

    @Override
    public boolean support(DefinedMessage message) {
        return chatDataHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, ChatData message) throws JsonProcessingException {
        chatDataHandler.call(ctx, message);
    }
}
