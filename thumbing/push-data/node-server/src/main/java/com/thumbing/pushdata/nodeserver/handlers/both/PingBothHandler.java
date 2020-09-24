package com.thumbing.pushdata.nodeserver.handlers.both;

import com.thumbing.pushdata.common.handlers.PingHandler;
import com.thumbing.pushdata.common.message.Ping;
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
public class PingBothHandler extends PingHandler implements ICenterDataHandler<Ping>, IDeviceDataHandler<Ping> {

}
