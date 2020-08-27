package com.thumbing.shared.config;

import com.thumbing.shared.condition.RabbitCondition;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;


/**
 * @author Stan Sai
 * @date 2020-07-04
 */
@Configuration
@Conditional(RabbitCondition.class)
public class RabbitConfig {
    public final static String pushDataExchange = "pushDataExchange";
    public final static String chatQueueName = "chatQueue";
    public final static String thumbQueueName = "thumbQueue";
    public final static String thumbRecordQueueName = "thumbRecordQueue";
    public final static String commentQueueName = "commentQueue";
    public final static String commentRecordQueueName = "commentRecordQueue";
    public final static String relationApplyQueueName = "relationApplyQueue";
    public final static String relationApplyRecordQueueName = "relationApplyRecordQueueName";
    public final static String chatRouteKey = "chat";
    public final static String thumbRouteKey = "thumb";
    public final static String commentRouteKey = "comment";
    public final static String relationApplyRouteKey = "relationApply";

    /**
     * 交换机
     * @return
     */
    @Bean(name = pushDataExchange)
    public TopicExchange pushDataExchange() {
        return new TopicExchange(pushDataExchange, true, false);
    }
    /**
     * 聊天消息队列
     * @return
     */
    @Bean(name = chatQueueName)
    public Queue chatQueue() {
        return new Queue(chatQueueName, true, false, false);
    }
    /**
     * 点赞发送队列
     * @return
     */
    @Bean(name = thumbQueueName)
    public Queue thumbQueue() {
        return new Queue(thumbQueueName, true, false, false);
    }
    /**
     * 点赞记录队列
     * @return
     */
    @Bean(name = thumbRecordQueueName)
    public Queue thumbRecordQueue() {
        return new Queue(thumbRecordQueueName, true, false, false);
    }
    /**
     * 评论发送队列
     * @return
     */
    @Bean(name = commentQueueName)
    public Queue commentQueue() {
        return new Queue(commentQueueName, true, false, false);
    }
    /**
     * 评论记录队列
     * @return
     */
    @Bean(name = commentRecordQueueName)
    public Queue commentRecordQueue() {
        return new Queue(commentRecordQueueName, true, false, false);
    }
    /**
     * 好友申请发送队列
     * @return
     */
    @Bean(name = relationApplyQueueName)
    public Queue relationApplyQueue(){return new Queue(relationApplyQueueName, true, false, false);}
    /**
     * 好友申请记录队列
     * @return
     */
    @Bean(name = relationApplyRecordQueueName)
    public Queue relationApplyRecordQueue(){return new Queue(relationApplyRecordQueueName, true, false, false);}
    /**
     * 聊天消息队列绑定路由
     * @return
     */
    @Bean
    public Binding pushDataExchangeBindChatQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(chatQueueName) Queue chatQueue) {
        return BindingBuilder.bind(chatQueue).to(pushDataExchange).with(chatRouteKey);
    }
    /**
     * 点赞发送队列绑定路由
     * @param pushDataExchange
     * @param thumbQueue
     * @return
     */
    @Bean
    public Binding pushDataExchangeBindThumbQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(thumbQueueName) Queue thumbQueue) {
        return BindingBuilder.bind(thumbQueue).to(pushDataExchange).with(thumbRouteKey);
    }
    /**
     * 点赞记录队列绑定路由
     * @param pushDataExchange
     * @param thumbQueue
     * @return
     */
    @Bean
    public Binding pushDataExchangeBindThumbRecordQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(thumbRecordQueueName) Queue thumbQueue) {
        return BindingBuilder.bind(thumbQueue).to(pushDataExchange).with(thumbRouteKey);
    }
    /**
     * 评论发送队列绑定路由
     * @param pushDataExchange
     * @param commentQueue
     * @return
     */
    @Bean
    public Binding pushDataExchangeBindCommentQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(commentQueueName) Queue commentQueue) {
        return BindingBuilder.bind(commentQueue).to(pushDataExchange).with(commentRouteKey);
    }
    /**
     * 评论记录队列绑定路由
     * @param pushDataExchange
     * @param commentQueue
     * @return
     */
    @Bean
    public Binding pushDataExchangeBindCommentRecordQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(commentRecordQueueName) Queue commentQueue) {
        return BindingBuilder.bind(commentQueue).to(pushDataExchange).with(commentRouteKey);
    }
    /**
     * 好友申请发送队列绑定路由
     * @param pushDataExchange
     * @param relationApplyQueue
     * @return
     */
    @Bean
    public Binding pushDataExchangeBindRelationApplyQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(relationApplyQueueName) Queue relationApplyQueue){
        return BindingBuilder.bind(relationApplyQueue).to(pushDataExchange).with(relationApplyRouteKey);
    }
    /**
     * 好友申请记录队列绑定路由
     * @param pushDataExchange
     * @param relationApplyRecordQueue
     * @return
     */
    @Bean
    public Binding pushDataExchangeBindrelationApplyRecordQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(relationApplyRecordQueueName) Queue relationApplyRecordQueue){
        return BindingBuilder.bind(relationApplyRecordQueue).to(pushDataExchange).with(relationApplyRouteKey);
    }
}
