package com.smc.smccloud.rabbitmq;

import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;

/**
 * @Author lyc
 * @Date 2023/9/12 9:45
 * @Descripton TODO
 */
public interface SendRcvOrderMqMsg {
    boolean sendOrderReceiveMsg(RabbitMqMessage rabbitMqMessage);

    boolean sendOrderStateMsg(RabbitMqMessage rabbitMqMessage);

    boolean sendOrderLogMsg(RabbitMqMessage rabbitMqMessage);
}
