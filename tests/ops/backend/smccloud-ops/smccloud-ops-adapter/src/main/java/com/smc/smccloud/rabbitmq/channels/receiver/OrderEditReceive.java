package com.smc.smccloud.rabbitmq.channels.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.smc.smccloud.config.AdapterReceive;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.adapter.order.AdapterReturn;
import com.smc.smccloud.model.adapter.order.OrderDeliveryModel;
import com.smc.smccloud.rabbitmq.InputChannelBinding;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * @Author lyc
 * @Date 2022/3/15 10:25
 * @Descripton (订单修改)  修改主单的队列  集约方式和出货方式（货齐出货）
 */
@Slf4j
@EnableBinding(InputChannelBinding.class)
public class OrderEditReceive extends AdapterReceive {
    @Autowired
    private SendMessage sendMessage;
    @Autowired
    private OrderService orderService;

    @StreamListener(value = InputChannelBinding.SMS_TO_OPS_ORDER_EDIT_RECEIVER,
            condition = "headers['x-flag']=='" + Constants.ERP_ORDER_EDIT + "'")
    public void receive(Message<RabbitMqMessage> message, @Header(AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {

        try {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            if (message != null) {
                RabbitMqMessage rabbitMqMessage = message.getPayload();
                log.info("订单修改-消息 {} {}", rabbitMqMessage.getRandomNumber(), rabbitMqMessage.getContent());

                OrderDeliveryModel orderDeliveryModel = JSON.parseObject(rabbitMqMessage.getContent(), OrderDeliveryModel.class);
                /**
                 * 调用订单修改接口：修改信息（集约方式、出货方式、收货地址信息、客户货期、开票类型和开票方式） 返回结果
                 */
                log.info("订单修改.接收门户参数  => {}", JSONObject.toJSONString(orderDeliveryModel));
                AdapterReturn result = orderService.handleOrderEditMQ(orderDeliveryModel);
                /**
                 * 确认一条消息：
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
                 */
                channel.basicAck(deliveryTag, false);
                adapterRabbitMqRedis.consumer(rabbitMqMessage);
                /**
                 * 订单修改结果：发送至订单修改结果队列 返回信息：订单号，ERP订单号，处理结果) 过滤标记：x-flag =
                 * 'erp-order-edit-return'
                 */
                // content, flag, dataType, system
                RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(result),
                        Constants.ERP_ORDER_EDIT_RERURN,
                        Constants.ORDER,
                        Constants.SMS);
                sendMessage.opsToSmsOrderEdit(rabbitMqMessageOrder);
                log.info("订单修改-消息消费成功: {} --result: {}", rabbitMqMessage.getRandomNumber(), result);
            }

        } catch (Exception e) {
            if (StringUtils.isNotBlank(e.getMessage()) && (e.getMessage().startsWith("feign.RetryableException")
                    || e.getMessage().contains("拒绝连接 (Connection refused)")
                    || e.getMessage().contains("Load balancer does not have available server for client"))) {
                log.error("订单修改消费失败： {}", e.getMessage());
                channel.basicNack(deliveryTag, false, true);
            } else {
                log.error("订单修改消费失败： {} --error: {}", message.getPayload(), e);
                messageHandle(channel, deliveryTag, message, e);
            }
        } finally {
            ThreadLocalMapUtil.clear();
        }
    }
}
