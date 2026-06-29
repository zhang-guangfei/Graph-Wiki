package com.smc.smccloud.rabbitmq;


import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;

/**
 * Author: B90034
 * Date: 2022-02-14 16:14
 * Description: 消息发送接口
 */
public interface SendMessage {

    void opsToSmsOrderCreate(RabbitMqMessage rabbitMqMessage);

    void opsToSmsOrderCancel(RabbitMqMessage rabbitMqMessage);

    void opsToSmsOrderEdit(RabbitMqMessage rabbitMqMessage);

    void opsToSmsYyqb(RabbitMqMessage rabbitMqMessage);

    void smsToOpsOrderCreate(RabbitMqMessage rabbitMqMessage);
}
