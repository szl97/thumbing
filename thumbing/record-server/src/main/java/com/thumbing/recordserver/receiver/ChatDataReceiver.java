package com.thumbing.recordserver.receiver;

import com.thumbing.recordserver.handler.ChatDataHandler;
import com.thumbing.shared.config.RabbitConfig;
import com.thumbing.shared.message.ChatDataMsg;
import com.thumbing.shared.thread.CustomThreadPool;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-08-11 23:28
 */
@Component
@RabbitListener(queues= RabbitConfig.chatQueueName)
public class ChatDataReceiver {

    @Autowired
    private ChatDataHandler chatDataHandler;
    @Autowired
    private CustomThreadPool threadPool;

    @RabbitHandler
    public void process(ChatDataMsg msg){
        threadPool.submit(
                ()->{
                    chatDataHandler.handleSession(msg);
                }
        );
        threadPool.submit(
                ()->{
                    chatDataHandler.handleRecord(msg);
                }
        );
    }
}
