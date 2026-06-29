package com.smc.smccloud.rabbitmq.channels.receiver;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.smc.smccloud.config.AdapterReceive;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.adapter.order.AdapterOrder;
import com.smc.smccloud.model.adapter.order.AdapterOrderResult;
import com.smc.smccloud.rabbitmq.InputChannelBinding;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-02-14 16:18
 * Description: 门户订单接单-消息接收器
 */
@Slf4j
@EnableBinding(InputChannelBinding.class)
public class OrderCreateReceiver extends AdapterReceive {

    @Resource
    private SendMessage sendMessage;
    @Resource
    private OrderService orderService;

    @StreamListener(value = InputChannelBinding.SMS_TO_OPS_ORDER_CREATE_RECEIVER,
            condition = "headers['x-flag']=='" + Constants.ERP_ORDER_CREATE + "'")
    public void receive(@Payload Message<RabbitMqMessage> message,
                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {

        try {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            if (message != null) {
                RabbitMqMessage rabbitMqMessage = message.getPayload();
                log.info("门户传过来的订单数据 : {}, {}", rabbitMqMessage.getRandomNumber(), rabbitMqMessage.getContent());
                List<AdapterOrder> adapterOrders = JSON.parseArray(rabbitMqMessage.getContent(), AdapterOrder.class);
                if (CollectionUtils.isEmpty(adapterOrders)) {
                    channel.basicAck(deliveryTag, false);// 手动确认
                }
                /*
                 * 订单信息（详见文档）、营业情报信息（营业情报号，是否预占库存） 1、调用订单创建接口，该接口返回订单接收结果，营业情报接收结果
                 * 2、订单创建接口中，需要判断是否包含营业情报号，如果包含且已预占库存时，需要消耗库存
                 */
                Date workTime = DateUtil.stringToDateTime(rabbitMqMessage.getDate());
                List<AdapterOrderResult> handelResult = orderService.handleOrderCreateMQ(adapterOrders, workTime);
                channel.basicAck(deliveryTag, false);// 手动确认
                // adapterRabbitMqRedis.consumer(rabbitMqMessage);

                /*
                 * 订单接收结果：发送至订单接收结果队列 返回信息如下： 订单信息：出库单号，ERP订单号，错误原因，接收状态：已退回、处理中等)
                 * 营业情报信息：营业情报号，是否释放库存， 过滤标记：x-flag = 'erp-order-create-return'
                 */
                // String content, String flag, String dataType, String system
                RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(handelResult),
                        Constants.ERP_ORDER_CREATE_RETURN,
                        Constants.ORDER,
                        Constants.SMS);
                rabbitMqMessageOrder.setRandomNumber(rabbitMqMessage.getRandomNumber());
                sendMessage.opsToSmsOrderCreate(rabbitMqMessageOrder);
                // 二次删除缓存 规避删除失败的情况
                // adapterRabbitMqRedis.consumer(rabbitMqMessage);
                log.info("订单接单-消息消费成功: {} {} --result: {}", rabbitMqMessage.getRandomNumber(), adapterOrders.get(0).getHddno(), handelResult);
            }
        } catch (Exception e) {
//            if (StringUtils.isNotBlank(e.getMessage()) && (e.getMessage().contains("feign.RetryableException")
//                    || e.getMessage().contains("拒绝连接 (Connection refused)")
//                    || e.getMessage().contains("Load balancer does not have available server for client"))
//                    || e.getMessage().contains("违反了 PRIMARY KEY 约束“PK_OrderDlvData”")) {
//                log.error("订单接单-消息消费失败: {}", e.getMessage());
//                channel.basicNack(deliveryTag, false, true);
//            } else {
//                log.error("订单接单-消息消费失败: {}, --errorMsg: {}", message.getPayload(), e.getMessage(), e);
//                messageHandle(channel, deliveryTag, message, e);
//            }
            // 优化接单处理
            log.error("订单接单-消息消费失败: {}, --errorMsg: {}", message.getPayload(), e.getMessage(), e);
            messageHandle(channel, deliveryTag, message, e);
        }
//        finally {
//            ThreadLocalMapUtil.clear();
//        }
    }

}
