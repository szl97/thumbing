package com.thumbing.pushdata.nodeserver.sender;

import com.thumbing.shared.config.RabbitConfig;
import com.thumbing.shared.message.ChatDataMsg;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-08-11 22:23
 */
@Component
public class ChatDataSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(ChatDataMsg msg){
        rabbitTemplate.convertAndSend(RabbitConfig.pushDataExchange,RabbitConfig.chatRouteKey, msg);
    }
}
