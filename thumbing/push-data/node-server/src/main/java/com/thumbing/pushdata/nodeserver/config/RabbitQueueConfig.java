package com.thumbing.pushdata.nodeserver.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Stan Sai
 * @date 2020-07-04
 */
@Configuration
public class RabbitQueueConfig {
    @Bean(name = "chatQueue")
    public Queue chatQueue() {
        return new Queue("chatQueue", true, false, false);
    }

    @Bean(name = "newsQueue")
    public Queue newsQueue() {
        return new Queue("newsQueue", true, false, false);
    }

    @Bean(name = "articleQueue")
    public Queue articleQueue() {
        return new Queue("articleQueue", true, false, false);
    }

    @Bean(name = "roastQueue")
    public Queue roastQueue() {
        return new Queue("roastQueue", true, false, false);
    }

    @Bean(name = "pushDataExchange")
    public TopicExchange pushDataExchange() {
        return new TopicExchange("pushDataExchange", true, false);
    }

    @Bean
    public Binding pushDataExchangeBindChatQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier("chatQueue") Queue chatQueue) {
        return BindingBuilder.bind(chatQueue).to(pushDataExchange).with("chat");
    }

    @Bean
    public Binding pushDataExchangeBindNewsQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier("newsQueue") Queue newsQueue) {
        return BindingBuilder.bind(newsQueue).to(pushDataExchange).with("news");
    }

    @Bean
    public Binding pushDataExchangeBindArticleQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier("articleQueue") Queue articleQueue) {
        return BindingBuilder.bind(articleQueue).to(pushDataExchange).with("article");
    }

    @Bean
    public Binding pushDataExchangeBindRoastQueue(
            @Qualifier("pushDataExchange") TopicExchange pushDataExchange,
            @Qualifier("roastQueue") Queue roastQueue) {
        return BindingBuilder.bind(roastQueue).to(pushDataExchange).with("roast");
    }
}
