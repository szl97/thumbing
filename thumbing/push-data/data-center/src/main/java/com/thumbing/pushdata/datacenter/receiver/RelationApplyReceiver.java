package com.thumbing.pushdata.datacenter.receiver;

import com.thumbing.pushdata.common.constants.PushDataTypeEnum;
import com.thumbing.pushdata.common.message.PushData;
import com.thumbing.pushdata.datacenter.handlers.PushDataHandler;
import com.thumbing.shared.config.RabbitConfig;
import com.thumbing.shared.rabbit.message.RelationApplyMsg;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 16:05
 */
@Component
@RabbitListener(queues= RabbitConfig.relationApplyQueueName)
public class RelationApplyReceiver {
    @Autowired
    private PushDataHandler pushDataHandler;

    @RabbitHandler
    public void process(RelationApplyMsg msg){
        List<Long> toIds = new ArrayList<>();
        toIds.add(msg.getToUserId());
        pushDataHandler.call(null, PushData.builder()
                .pushType(PushDataTypeEnum.RA)
                .data(msg.getRemark())
                .fromUserId(msg.getFromUserId())
                .fromUserName(msg.getFromUserName())
                .toUserIds(toIds).build()
        );
    }
}
