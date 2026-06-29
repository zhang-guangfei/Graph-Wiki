package com.smc.smccloud.rabbitmq.channels;

import com.smc.smccloud.config.SendAdapterMessageBase;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.rabbitmq.OutputChannelBinding;
import com.smc.smccloud.rabbitmq.SendMessage;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-02-14 16:16
 * Description: 消息发送服务
 */
@Service
@EnableBinding(OutputChannelBinding.class)
public class SendAdapterMessageImpl extends SendAdapterMessageBase implements SendMessage {

    @Resource
    private OutputChannelBinding outputChannelBinding;

    @Override
    public void opsToSmsOrderCreate(RabbitMqMessage rabbitMqMessage) {
        // this.rabbitMqRedis.producer(rabbitMqMessage);
        Message<RabbitMqMessage> message = this.message(rabbitMqMessage);
        Boolean success = this.outputChannelBinding.opsToSmsOrderCreate().send(message);
        this.saveMessage(rabbitMqMessage, success);
    }

    @Override
    public void opsToSmsOrderCancel(RabbitMqMessage rabbitMqMessage) {
        this.rabbitMqRedis.producer(rabbitMqMessage);
        Message<RabbitMqMessage> message = this.message(rabbitMqMessage);
        Boolean success = this.outputChannelBinding.opsToSmsOrderCancel().send(message);
        this.saveMessage(rabbitMqMessage, success);
    }

    @Override
    public void opsToSmsOrderEdit(RabbitMqMessage rabbitMqMessage) {
        this.rabbitMqRedis.producer(rabbitMqMessage);
        Message<RabbitMqMessage> message = this.message(rabbitMqMessage);
        Boolean success = this.outputChannelBinding.opsToSmsOrderEdit().send(message);
        this.saveMessage(rabbitMqMessage, success);
    }

    @Override
    public void opsToSmsYyqb(RabbitMqMessage rabbitMqMessage) {
        this.rabbitMqRedis.producer(rabbitMqMessage);
        Message<RabbitMqMessage> message = this.message(rabbitMqMessage);
        Boolean success = this.outputChannelBinding.opsToSmsYyqb().send(message);
        this.saveMessage(rabbitMqMessage, success);
    }

    @Override
    public void smsToOpsOrderCreate(RabbitMqMessage rabbitMqMessage) {
        /*this.rabbitMqRedis.producer(rabbitMqMessage);
        Message<RabbitMqMessage> message = this.message(rabbitMqMessage);
        Boolean success = this.outputChannelBinding.smsToOpsOrderCreate().send(message);
        this.saveMessage(rabbitMqMessage, success);*/
    }
}
