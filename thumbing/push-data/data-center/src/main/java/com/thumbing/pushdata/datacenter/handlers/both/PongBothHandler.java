package com.thumbing.pushdata.datacenter.handlers.both;

import com.thumbing.pushdata.common.handlers.PongHandler;
import com.thumbing.pushdata.common.message.Pong;
import com.thumbing.pushdata.datacenter.handlers.data.IDeviceDataHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class PongBothHandler extends PongHandler implements IDeviceDataHandler<Pong> {

}
