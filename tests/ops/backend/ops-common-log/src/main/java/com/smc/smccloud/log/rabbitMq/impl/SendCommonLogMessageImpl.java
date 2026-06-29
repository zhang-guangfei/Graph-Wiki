package com.smc.smccloud.log.rabbitMq.impl;

import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.SendMessageBase;
import com.smc.smccloud.log.rabbitMq.OutputChannelCommonLogBinding;
import com.smc.smccloud.log.rabbitMq.SendCommonLogMessage;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;

import javax.annotation.Resource;


/**
 * @Author lyc
 * @Date 2023/5/29 9:51
 * @Descripton TODO
 */
@EnableBinding(OutputChannelCommonLogBinding.class)
public class SendCommonLogMessageImpl extends SendMessageBase implements SendCommonLogMessage {

    @Resource
    private OutputChannelCommonLogBinding outputChannelCommonLogBinding;

    @Override
    public boolean sendCommonLogMsg(RabbitMqMessage rabbitMqMessage) {
        Message<RabbitMqMessage> message = message(rabbitMqMessage);
        return outputChannelCommonLogBinding.toCommonLog().send(message);
    }
}
