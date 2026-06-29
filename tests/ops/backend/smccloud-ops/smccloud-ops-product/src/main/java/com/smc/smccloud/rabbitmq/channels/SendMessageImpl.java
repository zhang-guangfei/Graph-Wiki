package com.smc.smccloud.rabbitmq.channels;

import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.SendMessageBase;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.rabbitmq.OutputChannelBinding;
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
    public Boolean sendInventorySupplier(RabbitMqMessage rabbitMqMessage) {
        //  待发送的消息 (producer:) 存入redis （3600 * 24 * 15）
        rabbitMqRedis.producer(rabbitMqMessage);
        // 对消息进行设置
        Message<RabbitMqMessage> message = message(rabbitMqMessage);
        boolean success = outputChannelBinding.toInventorySupplier().send(message);
        // 将待生产的删除  将待生产/待消费 存入redis
        saveMessage(rabbitMqMessage, success);
        return success;
    }
}
