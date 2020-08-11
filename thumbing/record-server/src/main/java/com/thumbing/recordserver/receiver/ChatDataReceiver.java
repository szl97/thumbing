package com.thumbing.recordserver.receiver;

import com.thumbing.shared.config.RabbitConfig;
import com.thumbing.shared.message.ChatDataMsg;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-08-11 23:28
 */
@Component
@RabbitListener(queues= RabbitConfig.chatQueueName)
public class ChatDataReceiver {

    @RabbitHandler
    public void process(ChatDataMsg msg){

    }
}
