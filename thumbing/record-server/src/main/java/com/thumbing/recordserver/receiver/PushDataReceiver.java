package com.thumbing.recordserver.receiver;

import com.thumbing.recordserver.handler.PushDataHandler;
import com.thumbing.shared.config.RabbitConfig;
import com.thumbing.shared.entity.mongo.record.PushDataRecord;
import com.thumbing.shared.message.RelationApplyMsg;
import com.thumbing.shared.thread.CustomThreadPool;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @RabbitListener(queues= RabbitConfig.relationApplyRecordQueueName)
    public void process(RelationApplyMsg msg){
        threadPool.submit(()-> pushDataHandler.handle(msg));
    }

    @RabbitHandler
    @RabbitListener(queues= RabbitConfig.commentRecordQueueName)
    public void processComment(PushDataRecord msg) {
        threadPool.submit(()-> pushDataHandler.handle(msg));
    }

    @RabbitHandler
    @RabbitListener(queues= RabbitConfig.thumbRecordQueueName)
    public void processThumb(PushDataRecord msg) {
        threadPool.submit(()-> pushDataHandler.handle(msg));
    }
}
