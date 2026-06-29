package com.smc.smccloud.rabbitmq.channels;

import com.alibaba.fastjson.JSONObject;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.SendMessageBase;
import com.smc.smccloud.core.utils.IpUtil;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.rabbitmq.OutputChannelBinding;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.rabbitmq.SendRcvOrderMqMsg;
import com.smc.smccloud.service.OrderLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/9/12 9:46
 * @Descripton TODO
 */
@EnableBinding(OutputChannelBinding.class)
@Slf4j
public class SendRcvOrderMqMsgImpl extends SendMessageBase implements SendRcvOrderMqMsg {

    @Resource
    private OutputChannelBinding outputChannelBinding;

    @Resource
    private OrderLogService orderLogService;

    @Override
    public boolean sendOrderReceiveMsg(RabbitMqMessage rabbitMqMessage) {
        log.info("进入sendOrderReceiveMsg {}",rabbitMqMessage.toString());
        //  待发送的消息 (producer:) 存入redis （3600 * 24 * 15）
        rabbitMqRedis.producer(rabbitMqMessage);
        // 对消息进行设置
        Message<RabbitMqMessage> message = message(rabbitMqMessage);
        boolean success = outputChannelBinding.toOrderRccevie().send(message);
        log.info("{} 发送接单队列 {}",rabbitMqMessage,success);
        // 将待生产的删除  将待生产/待消费 存入redis
        saveMessage(rabbitMqMessage, success);
        log.info("将待生产/待消费 存入redis完毕, {}",rabbitMqMessage.getRandomNumber());
        return success;
    }
    @Override
    public boolean sendOrderStateMsg(RabbitMqMessage rabbitMqMessage) {
        //  待发送的消息 (producer:) 存入redis （3600 * 24 * 15）
        //rabbitMqRedis.producer(rabbitMqMessage);
        // 对消息进行设置
        Message<RabbitMqMessage> message = message(rabbitMqMessage);
        boolean success = outputChannelBinding.toOrderState().send(message);

        OrderStateVO orderStateVO = JSONObject.parseObject(rabbitMqMessage.getContent(), OrderStateVO.class);
        if (orderStateVO.getIsAddLog() != null && orderStateVO.getIsAddLog()) {
            OrderLogVO log = new OrderLogVO();
            log.setOrderNo(orderStateVO.getOrderNo());
            log.setOptType(orderStateVO.getStateCode());
            log.setCreateTime(new Date());
            log.setOptUserName(orderStateVO.getOptUserName());
            log.setOptUserId(orderStateVO.getOptUserNo());
            log.setDescription(OrderStateEnum.getStateNameByCode(log.getOptType()));
            log.setOptTime(new Date());
            String ipAddress = IpUtil.getIpAddress();
            log.setIp(ipAddress);
            orderLogService.sendOrderLogMsgToMQ(log);
        }
        // 将待生产的删除  将待生产/待消费 存入redis
        //saveMessage(rabbitMqMessage, success);
        return success;
    }

    @Override
    public boolean sendOrderLogMsg(RabbitMqMessage rabbitMqMessage) {
        //  待发送的消息 (producer:) 存入redis （3600 * 24 * 15）
        //rabbitMqRedis.producer(rabbitMqMessage);
        // 对消息进行设置
        Message<RabbitMqMessage> message = message(rabbitMqMessage);
        boolean success = outputChannelBinding.toOrderLog().send(message);
        // 将待生产的删除  将待生产/待消费 存入redis
        //saveMessage(rabbitMqMessage, success);
        return success;
    }
}
