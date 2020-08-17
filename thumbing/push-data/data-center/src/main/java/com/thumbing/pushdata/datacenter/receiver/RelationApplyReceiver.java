package com.thumbing.pushdata.datacenter.receiver;

import com.thumbing.shared.message.PushDataTypeEnum;
import com.thumbing.pushdata.common.message.PushData;
import com.thumbing.pushdata.datacenter.handlers.PushDataHandler;
import com.thumbing.shared.config.RabbitConfig;
import com.thumbing.shared.message.RelationApplyMsg;
import com.thumbing.shared.thread.CustomThreadPool;
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
    @Autowired
    private CustomThreadPool threadPool;

    @RabbitHandler
    public void process(RelationApplyMsg msg) {
        threadPool.submit(
                () -> {
                    List<Long> toIds = new ArrayList<>();
                    toIds.add(msg.getToUserId());
                    pushDataHandler.call(null, PushData.builder()
                            .dataId(msg.getDataId())
                            .pushType(PushDataTypeEnum.RA)
                            .data(msg.getRemark())
                            .fromUserId(msg.getFromUserId())
                            .fromUserName(msg.getFromUserName())
                            .toUserIds(toIds).build()
                    );
                }
        );
    }
}
