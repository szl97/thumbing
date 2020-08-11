package com.thumbing.pushdata.nodeserver.handlers.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.message.GroupData;
import com.thumbing.pushdata.nodeserver.handlers.GroupDataHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class GroupDataFromDeviceHandler extends GroupDataHandler implements IDeviceDataHandler<GroupData> {
    @Override
    public void call(ChannelHandlerContext ctx, GroupData message) throws JsonProcessingException {

        //todo: 群聊消息收到后先写到redis
        //todo...

        super.call(ctx, message);
    }
}
