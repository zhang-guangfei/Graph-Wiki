package com.smc.smccloud.core.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
public abstract class SendMessageBase {

    @Autowired
    public RabbitmqRedis rabbitMqRedis;

    public Message<RabbitMqMessage> message(RabbitMqMessage rabbitMqMessage) {
        Message<RabbitMqMessage> message = MessageBuilder.withPayload(rabbitMqMessage)
                /**
                 * 设置消息的延迟时间，首次发送，不设置延迟时间，直接发送
                 */
                .setHeader(RabbitMqCommonProperties.X_DELAY_HEADER, rabbitMqMessage.getDelaySeconds())
                /**
                 * 设置消息已经重试的次数，首次发送，设置为0
                 */
                .setHeader(RabbitMqCommonProperties.X_RETRIES_HEADER, rabbitMqMessage.getRetireNumber())
                /**
                 * 定义过滤消息标记，设置为flag
                 */
                .setHeader(RabbitMqCommonProperties.X_FLAG, rabbitMqMessage.getFlag())
                .build();
        return message;
    }

    public void saveMessage(RabbitMqMessage rabbitMqMessage, Boolean success) {
        String result = success ? "待消费" : "发送失败";
        rabbitMqMessage.setStatus(result);
        log.debug(rabbitMqMessage.toString() + "《《《发送结果》》" + result);
        if (success) {
            rabbitMqRedis.resetRabbitmqRedis(RabbitMqConstants.RABBITMQ_REDIS_PRODUCER, RabbitMqConstants.RABBITMQ_REDIS_CONSUMER, rabbitMqMessage);
            return;
        }
        rabbitMqRedis.resetRabbitmqRedis(RabbitMqConstants.RABBITMQ_REDIS_PRODUCER, RabbitMqConstants.RABBITMQ_REDIS_PRODUCER_FAILURE, rabbitMqMessage);
    }
}
