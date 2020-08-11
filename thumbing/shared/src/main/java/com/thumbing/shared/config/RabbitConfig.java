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
    public final static String momentQueueName = "momentQueue";
    public final static String articleQueueName = "articleQueue";
    public final static String roastQueueName = "roastQueue";
    public final static String relationApplyQueueName = "relationApplyQueue";
    public final static String relationApplyRecordQueueName = "relationApplyRecordQueueName";

    public final static String chatRouteKey = "chat";
    public final static String momentRouteKey = "moment";
    public final static String articleRouteKey = "article";
    public final static String roastRouteKey = "roast";
    public final static String relationApplyRouteKey = "relationApply";


    @Bean(name = pushDataExchange)
    public TopicExchange pushDataExchange() {
        return new TopicExchange(pushDataExchange, true, false);
    }

    @Bean(name = chatQueueName)
    public Queue chatQueue() {
        return new Queue(chatQueueName, true, false, false);
    }

    @Bean(name = momentQueueName)
    public Queue momentsQueue() {
        return new Queue(momentQueueName, true, false, false);
    }

    @Bean(name = articleQueueName)
    public Queue articleQueue() {
        return new Queue(articleQueueName, true, false, false);
    }

    @Bean(name = roastQueueName)
    public Queue roastQueue() {
        return new Queue(roastQueueName, true, false, false);
    }

    @Bean(name = relationApplyQueueName)
    public Queue relationApplyQueue(){return new Queue(relationApplyQueueName, true, false, false);}

    @Bean(name = relationApplyRecordQueueName)
    public Queue relationApplyRecordQueue(){return new Queue(relationApplyRecordQueueName, true, false, false);}

    @Bean
    public Binding pushDataExchangeBindChatQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(chatQueueName) Queue chatQueue) {
        return BindingBuilder.bind(chatQueue).to(pushDataExchange).with(chatRouteKey);
    }

    @Bean
    public Binding pushDataExchangeBindMomentssQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(momentQueueName) Queue momentsQueue) {
        return BindingBuilder.bind(momentsQueue).to(pushDataExchange).with(momentRouteKey);
    }

    @Bean
    public Binding pushDataExchangeBindArticleQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(articleQueueName) Queue articleQueue) {
        return BindingBuilder.bind(articleQueue).to(pushDataExchange).with(articleRouteKey);
    }

    @Bean
    public Binding pushDataExchangeBindRoastQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(roastQueueName) Queue roastQueue) {
        return BindingBuilder.bind(roastQueue).to(pushDataExchange).with(roastRouteKey);
    }

    @Bean
    public Binding pushDataExchangeBindRelationApplyQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(relationApplyQueueName) Queue relationApplyQueue){
        return BindingBuilder.bind(relationApplyQueue).to(pushDataExchange).with(relationApplyRouteKey);
    }

    @Bean
    public Binding pushDataExchangeBindrelationApplyRecordQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier(relationApplyRecordQueueName) Queue relationApplyRecordQueue){
        return BindingBuilder.bind(relationApplyRecordQueue).to(pushDataExchange).with(relationApplyRouteKey);
    }
}
