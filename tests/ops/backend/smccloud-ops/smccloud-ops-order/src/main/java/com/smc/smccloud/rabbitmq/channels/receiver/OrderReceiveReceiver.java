package com.smc.smccloud.rabbitmq.channels.receiver;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.Receive;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.OrderSales.OrderSalesDO;
import com.smc.smccloud.rabbitmq.InputChannelBinding;
import com.smc.smccloud.service.ReceiveOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-05-18 10:31
 * Description: 订单接单-消息接收器
 */
@Slf4j
@EnableBinding(InputChannelBinding.class)
public class OrderReceiveReceiver extends Receive {

    @Autowired
    @Lazy
    private ReceiveOrderService receiveOrderService;

    @StreamListener(value = InputChannelBinding.OPS_ORDER_RECEIVE_RECEIVER,
            condition = "headers['x-flag']=='" + Constants.OPS_ORDER_RECEIVE + "'")
    public void receive(@Payload Message<RabbitMqMessage> message,
                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
        try {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            if (PublicUtil.isNotEmpty(message)) {
                RabbitMqMessage rabbitMqMessage = message.getPayload();
                //log.info("接收接入订单消息 msg = {}", rabbitMqMessage);

                if (StringUtils.isNotBlank(rabbitMqMessage.getContent())) {
                    OrderSalesDO orderSalesInfo = JSON.parseObject(rabbitMqMessage.getContent(), OrderSalesDO.class);
                    if (StringUtils.isNotBlank(orderSalesInfo.getRorderNo())) {
                        ResultVo<String> result = receiveOrderService.receiveOrderByOrderNo(orderSalesInfo.getRorderNo());
                        log.info("订单接入消息队列 {} >>> {}", orderSalesInfo.getRorderNo(), result);
                        rabbitMqRedis.consumer(rabbitMqMessage); // 从redis中删除
                    }
                }

                /*
                 * 确认一条消息：
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
                 */
                channel.basicAck(deliveryTag, false);
                rabbitMqRedis.consumer(rabbitMqMessage); // 从redis中删除 二次删除 避免小概率删不掉
                log.debug("消息消费成功 {} {}{}", rabbitMqMessage.getFlag(), rabbitMqMessage.getDataType(), rabbitMqMessage.getRandomNumber());
            }
        } catch (Exception ex) {
            log.error("消息消费异常: {}", ex.getMessage(), ex);
            messageHandle(channel, deliveryTag, message, ex);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GRANT_TYPE);
        }
    }
}
