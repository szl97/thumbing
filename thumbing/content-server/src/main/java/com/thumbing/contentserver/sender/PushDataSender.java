package com.thumbing.contentserver.sender;

import com.thumbing.shared.config.RabbitConfig;
import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 14:13
 */
@Component
public class PushDataSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendThumb(PushDataRecord msg){
        rabbitTemplate.convertAndSend(RabbitConfig.pushDataExchange,RabbitConfig.thumbRouteKey, msg);
    }

    public void sendComment(PushDataRecord msg){
        rabbitTemplate.convertAndSend(RabbitConfig.pushDataExchange,RabbitConfig.commentRouteKey, msg);
    }
}
