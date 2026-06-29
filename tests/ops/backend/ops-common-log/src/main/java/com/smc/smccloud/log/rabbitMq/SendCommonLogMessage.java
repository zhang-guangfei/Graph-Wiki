package com.smc.smccloud.log.rabbitMq;

import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;

/**
 * @Author lyc
 * @Date 2023/5/29 9:27
 * @Descripton TODO
 */
public interface SendCommonLogMessage {

    boolean sendCommonLogMsg(RabbitMqMessage rabbitMqMessage);
}
