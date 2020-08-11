package com.thumbing.pushdata.datacenter.handlers.data;

import com.thumbing.pushdata.common.message.Confirm;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.datacenter.handlers.ConfirmHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class ConfirmDataFlowHandler extends ConfirmHandler implements IDeviceDataHandler<Confirm> {

}
