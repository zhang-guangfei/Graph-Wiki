//package com.smc.smccloud.rabbitmq.channels.receiver;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.rabbitmq.client.Channel;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.Objects;
//
///**
// * Author: C14717
// * Date: 2022-06-16
// * Description: api操作日志-消息接收器
// */
//@Slf4j
//@EnableBinding(InputChannelBinding.class)
//public class OrderLogReceiver extends Receive {
//    @Resource
//    ActionLogMapper actionLogMapper;
//
//    @StreamListener(value = InputChannelBinding.OPS_API_LOG_RECEIVER,
//            condition = "headers['x-flag']=='" + Constants.OPS_API_LOG + "'")
//    public void receive(@Payload Message<RabbitMqMessage> message,
//                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
//                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
//
//        ActionLog logVo = new ActionLog();
//        try {
//            if (Objects.nonNull(message)) {
//                RabbitMqMessage rabbitMqMessage = message.getPayload();
//
//                if (StringUtils.isNotBlank(rabbitMqMessage.getContent())) {
//                    logVo = JSON.parseObject(rabbitMqMessage.getContent(), ActionLog.class);
//                    log.debug("日志 -> ApiLog = {}", logVo);
//                    actionLogMapper.insertSelective(logVo);
//                }
//
//                /*
//                 * 确认一条消息：
//                 * deliveryTag:该消息的index
//                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
//                 */
//                channel.basicAck(deliveryTag, false);
//                //rabbitMqRedis.consumer(rabbitMqMessage); // 从redis中删除
//                log.debug("消息消费成功 {} {}{}", rabbitMqMessage.getFlag(), rabbitMqMessage.getDataType(), rabbitMqMessage.getRandomNumber());
//            }
//        } catch (Exception ex) {
//            log.error("[日志-消息接收]消息消费异常: {},异常数据: {}", ex.getMessage(), JSONObject.toJSONString(logVo));
//            //messageHandle(channel, deliveryTag, message, ex);
//            try {
//                channel.basicNack(deliveryTag, false, true);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}
