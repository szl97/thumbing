package com.thumbing.usermanagement.sender;

import com.thumbing.shared.config.RabbitConfig;
import com.thumbing.usermanagement.sender.dto.RelationApplyMsg;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 15:13
 */
@Component
public class RelationApplySender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(RelationApplyMsg msg){
        rabbitTemplate.convertAndSend(RabbitConfig.pushDataExchange,RabbitConfig.relationApplyRouteKey, msg);
    }
}
