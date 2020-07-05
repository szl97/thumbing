package com.loserclub.pushdata.common.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;


/**
 * @author Stan Sai
 * @date 2020-07-04
 */
public interface RabbitConfirmCallBack extends RabbitTemplate.ConfirmCallback {
}
