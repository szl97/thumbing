package com.thumbing.pushdata.nodeserver.handlers.center;

import com.thumbing.pushdata.common.message.ChatData;
import com.thumbing.pushdata.nodeserver.handlers.ChatDataHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-08-11 22:09
 */
@Slf4j
@Component
@Data
public class ChatDataFromCenterHandler extends ChatDataHandler implements ICenterDataHandler<ChatData> {
}
