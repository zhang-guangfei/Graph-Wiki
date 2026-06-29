package com.smc.smccloud.rabbitmq.channels.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.Receive;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.OrderLog.OrderLogDO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.rabbitmq.InputChannelBinding;
import com.smc.smccloud.service.OrderLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-01-05 10:43
 * Description: 订单操作日志-消息接收器
 */
@Slf4j
@EnableBinding(InputChannelBinding.class)
public class OrderLogReceiver extends Receive {

    @Resource
    private OrderLogService orderLogService;

    @StreamListener(value = InputChannelBinding.OPS_ORDER_LOG_RECEIVER,
            condition = "headers['x-flag']=='" + Constants.OPS_ORDER_LOG + "'")
    public void receive(@Payload Message<RabbitMqMessage> message,
                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
        OrderLogVO orderLogVO = new OrderLogVO();
        try {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            if (PublicUtil.isNotEmpty(message)) {
                RabbitMqMessage rabbitMqMessage = message.getPayload();
                //log.info("Receive order_log msg = {}", rabbitMqMessage);

                if (StringUtils.isNotBlank(rabbitMqMessage.getContent())) {
                    orderLogVO = JSON.parseObject(rabbitMqMessage.getContent(), OrderLogVO.class);
                    log.debug("日志 -> orderLog = {}", orderLogVO);
                    OrderLogDO orderLogDO = BeanCopyUtil.copy(orderLogVO, OrderLogDO.class);
                    orderLogService.addLog(orderLogDO);
                }

                /*
                 * 确认一条消息：
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
                 */
                channel.basicAck(deliveryTag, false);
                // rabbitMqRedis.consumer(rabbitMqMessage); // 从redis中删除
                log.debug("消息消费成功 {} {}{}", rabbitMqMessage.getFlag(), rabbitMqMessage.getDataType(), rabbitMqMessage.getRandomNumber());
            }
        } catch (Exception ex) {
            log.error("[日志-消息接收]消息消费异常: {},异常数据: {}", ex.getMessage(), JSONObject.toJSONString(orderLogVO));
            ex.printStackTrace();
            messageHandle(channel, deliveryTag, message, ex);
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GRANT_TYPE);
        }
    }
}
