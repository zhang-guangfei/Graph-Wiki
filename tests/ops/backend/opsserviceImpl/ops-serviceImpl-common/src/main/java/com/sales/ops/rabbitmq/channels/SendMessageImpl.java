package com.sales.ops.rabbitmq.channels;

import com.sales.ops.common.rabbitmq.RabbitMqMessage;
import com.sales.ops.common.rabbitmq.SendMessageBase;
import com.sales.ops.rabbitmq.OutputChannelBinding;
import com.sales.ops.rabbitmq.SendMessage;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;

import javax.annotation.Resource;

/**
 * 消息发送者接口实现类
 */
@EnableBinding(OutputChannelBinding.class)
public class SendMessageImpl extends SendMessageBase implements SendMessage {

    @Resource
    private OutputChannelBinding outputChannelBinding;

    @Override
    public boolean sendOrderStateMsg(RabbitMqMessage rabbitMqMessage) {
        //  待发送的消息 (producer:) 存入redis （3600 * 24 * 15）
        //rabbitMqRedis.producer(rabbitMqMessage);
        // 对消息进行设置
        Message<RabbitMqMessage> message = message(rabbitMqMessage);
        boolean success = outputChannelBinding.toOrderState().send(message);
        // 将待生产的删除  将待生产/待消费 存入redis
        //saveMessage(rabbitMqMessage, success);
        return success;
    }

    @Override
    public boolean sendApiLogMsg(RabbitMqMessage rabbitMqMessage) {
        //  待发送的消息 (producer:) 存入redis （3600 * 24 * 15）
        rabbitMqRedis.producer(rabbitMqMessage);
        // 对消息进行设置
        Message<RabbitMqMessage> message = message(rabbitMqMessage);
        boolean success = outputChannelBinding.toApiLog().send(message);
        // 将待生产的删除  将待生产/待消费 存入redis
        saveMessage(rabbitMqMessage, success);
        return success;
    }

    @Override
    public boolean sendCommonLogMsg(RabbitMqMessage rabbitMqMessage) {
        Message<RabbitMqMessage> message = message(rabbitMqMessage);
        return outputChannelBinding.toCommonLog().send(message);
    }

    @Override
    public boolean sendInvoiceProcessMsg(RabbitMqMessage rabbitMqMessage) {
        Message<RabbitMqMessage> message = message(rabbitMqMessage);
        return outputChannelBinding.toInvoiceProcess().send(message);
    }

}
