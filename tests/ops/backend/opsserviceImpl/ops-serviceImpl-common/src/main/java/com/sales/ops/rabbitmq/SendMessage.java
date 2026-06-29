package com.sales.ops.rabbitmq;


import com.sales.ops.common.rabbitmq.RabbitMqMessage;

/**
 * 消息生产类--接口
 */
public interface SendMessage {

	boolean sendOrderStateMsg(RabbitMqMessage rabbitMqMessage);

	boolean sendApiLogMsg(RabbitMqMessage rabbitMqMessage);
	boolean sendCommonLogMsg(RabbitMqMessage rabbitMqMessage);

	boolean sendInvoiceProcessMsg(RabbitMqMessage rabbitMqMessage);
}
