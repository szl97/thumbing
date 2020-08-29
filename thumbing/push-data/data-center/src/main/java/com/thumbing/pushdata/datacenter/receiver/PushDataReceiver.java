package com.thumbing.pushdata.datacenter.receiver;

import com.thumbing.shared.entity.mongo.record.PushDataRecord;
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

public class PushDataReceiver {
    @Autowired
    private PushDataHandler pushDataHandler;
    @Autowired
    private CustomThreadPool threadPool;

    @RabbitHandler
    @RabbitListener(queues= RabbitConfig.relationApplyQueueName)
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
                            .time(msg.getTime())
                            .toUserIds(toIds).build()
                    );
                }
        );
    }

    @RabbitHandler
    @RabbitListener(queues= RabbitConfig.commentQueueName)
    public void processComment(PushDataRecord msg) {
        threadPool.submit(
                () -> {
                    List<Long> toIds = new ArrayList<>();
                    toIds.add(msg.getToUserId());
                    pushDataHandler.call(null, PushData.builder()
                            .dataId(Long.parseLong(msg.getDataId()))
                            .pushType(msg.getPushType())
                            .fromUserId(msg.getFromUserId())
                            .fromUserName(msg.getFromUserName())
                            .fromUserNickName(msg.getFromUserNickName())
                            .data(msg.getData())
                            .time(msg.getCreateTime())
                            .toUserIds(toIds).build()
                    );
                }
        );
    }

    @RabbitHandler
    @RabbitListener(queues= RabbitConfig.thumbQueueName)
    public void processThumb(PushDataRecord msg) {
        threadPool.submit(
                () -> {
                    List<Long> toIds = new ArrayList<>();
                    toIds.add(msg.getToUserId());
                    pushDataHandler.call(null, PushData.builder()
                            .dataId(Long.parseLong(msg.getDataId()))
                            .pushType(msg.getPushType())
                            .time(msg.getCreateTime())
                            .toUserIds(toIds).build()
                    );
                }
        );
    }
}
