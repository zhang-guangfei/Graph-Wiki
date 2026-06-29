package com.smc.smccloud.rabbitmq;


import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;

/**
 * 消息生产类--接口
 */
public interface SendMessage {

	boolean sendOrderStateMsg(RabbitMqMessage rabbitMqMessage);

	boolean sendOrderLogMsg(RabbitMqMessage rabbitMqMessage);

	boolean sendOrderReceiveMsg(RabbitMqMessage rabbitMqMessage);

	boolean sendInvoiceProcessMsg(RabbitMqMessage rabbitMqMessage);
}
