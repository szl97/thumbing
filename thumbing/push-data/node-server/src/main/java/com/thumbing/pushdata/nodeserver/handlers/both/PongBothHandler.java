package com.thumbing.pushdata.nodeserver.handlers.both;

import com.thumbing.pushdata.common.handlers.PongHandler;
import com.thumbing.pushdata.common.message.Pong;
import com.thumbing.pushdata.nodeserver.handlers.center.ICenterDataHandler;
import com.thumbing.pushdata.nodeserver.handlers.device.IDeviceDataHandler;
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
public class PongBothHandler extends PongHandler implements ICenterDataHandler<Pong>, IDeviceDataHandler<Pong> {

}
