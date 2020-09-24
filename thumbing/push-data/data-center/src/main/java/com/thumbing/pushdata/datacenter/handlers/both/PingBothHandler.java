package com.thumbing.pushdata.datacenter.handlers.both;

import com.thumbing.pushdata.common.handlers.PingHandler;
import com.thumbing.pushdata.common.message.Ping;
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
public class PingBothHandler extends PingHandler implements IDeviceDataHandler<Ping> {

}
