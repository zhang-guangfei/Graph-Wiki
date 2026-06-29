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
import com.smc.smccloud.model.adapter.order.OrderCancelDTO;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.rabbitmq.InputChannelBinding;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-02-14 16:18
 * Description: 门户订单取消-消息接收器
 */
@Slf4j
@EnableBinding(InputChannelBinding.class)
public class OrderCancelReceiver extends AdapterReceive {

    @Resource
    private SendMessage sendMessage;
    @Resource
    private OrderService orderService;

    @StreamListener(value = InputChannelBinding.SMS_TO_OPS_ORDER_CANCEL_RECEIVER,
            condition = "headers['x-flag']=='" + Constants.ERP_ORDER_CANCEL + "'")
    public void receive(@Payload Message<RabbitMqMessage> message,
                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {

        try {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            if (message != null) {
                RabbitMqMessage rabbitMqMessage = message.getPayload();
                log.info("订单取消-消息 {} {}", rabbitMqMessage.getRandomNumber(), rabbitMqMessage.getContent());
                OrderCancelDTO info = JSON.parseObject(rabbitMqMessage.getContent(), OrderCancelDTO.class);

                /*
                 * 调用取消订单接口
                 */
                List<OrderCancelResult> result = orderService.handleOrderCancelMQ(info);
                log.info("取消订单推送门户参数(队列): {}", JSONObject.toJSONString(result));

                /*
                 * 返回结果
                 */
                channel.basicAck(deliveryTag, false);// 手动确认
                adapterRabbitMqRedis.consumer(rabbitMqMessage);

                /*
                 * 订单取消结果：发送至订单取消结果队列 返回信息：订单号，ERP订单号，处理结果) 过滤标记：x-flag =
                 * 'erp-order-cancel-return'
                 */
                RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(result),
                        Constants.ERP_ORDER_CANCEL_RETURN,
                        Constants.ORDER,
                        Constants.SMS);
                sendMessage.opsToSmsOrderCancel(rabbitMqMessageOrder);
                log.info("订单取消-消息消费成功: {} --result: {}", rabbitMqMessage.getRandomNumber(), result);
            }
        } catch (Exception e) {
            if (StringUtils.isNotBlank(e.getMessage()) && (e.getMessage().startsWith("feign.RetryableException")
                    || e.getMessage().contains("拒绝连接 (Connection refused)")
                    || e.getMessage().contains("Load balancer does not have available server for client"))) {
                log.error("订单取消-消息消费失败: {}", e.getMessage());
                channel.basicNack(deliveryTag, false, true);
            } else {
                log.error("订单取消-消息消费失败: {}, errorMsg: {}", message.getPayload(), e.getMessage(), e);
                messageHandle(channel, deliveryTag, message, e);
            }
        } finally {
            ThreadLocalMapUtil.clear();
        }
    }

}
