package com.smc.smccloud.config;

import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
public abstract class SendAdapterMessageBase {

    @Autowired
    public AdapterRabbitMqRedis rabbitMqRedis;

    public SendAdapterMessageBase() {
    }

    public Message<RabbitMqMessage> message(RabbitMqMessage rabbitMqMessage) {
        return MessageBuilder.withPayload(rabbitMqMessage).setHeader("x-delay", rabbitMqMessage.getDelaySeconds()).setHeader("x-retries", rabbitMqMessage.getRetireNumber()).setHeader("x-flag", rabbitMqMessage.getFlag()).build();
    }

    public void saveMessage(RabbitMqMessage rabbitMqMessage, Boolean success) {
        String result = success ? "待消费" : "发送失败";
        rabbitMqMessage.setStatus(result);
        this.rabbitMqRedis.resetRabbitmqRedis(rabbitMqMessage);
        log.info("《《《发送结果》》==== {}{}:{}", rabbitMqMessage.getDataType(), rabbitMqMessage.getRandomNumber(), result);
    }
}