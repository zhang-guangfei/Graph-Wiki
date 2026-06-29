package com.smc.smccloud.config;

import com.rabbitmq.client.Channel;
import com.smc.smccloud.core.rabbitmq.RabbitMqCommonProperties;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
public abstract class AdapterReceive {

    @Autowired
    public AdapterRabbitMqRedis adapterRabbitMqRedis;

    public void messageHandle(Channel channel, long deliveryTag, Message<RabbitMqMessage> message, Exception e) {
        try {
            RabbitMqMessage rabbitMqMessage = message.getPayload();
//            List<AdapterOrder> adapterOrders = JSON.parseArray(rabbitMqMessage.getContent(), AdapterOrder.class);
//            List<AdapterOrder> newList = adapterOrders.subList(1, 15);
//            log.error("MQ消费异常: {}, {}", e.getMessage(), JSONObject.toJSONString(newList));
            log.error("MQ消费异常: {}, {}", e.getMessage(), rabbitMqMessage);

            // 获取重试次数
            int retries = adapterRabbitMqRedis.getRetrie(rabbitMqMessage);
            log.info("当前消息消费失败重试次数: {}", retries);
            if (retries < RabbitMqCommonProperties.X_RETRIES_TOTAL) {
                retries++; // 重试计数
                rabbitMqMessage.setRetireNumber(retries);
                rabbitMqMessage.setDelaySeconds(RabbitMqCommonProperties.ruleMap.get(retries));
                adapterRabbitMqRedis.consumerRedisReset(rabbitMqMessage);
                /*
                 * 拒绝确认消息:
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                 * requeue：被拒绝的是否重新入队列
                 */
                channel.basicNack(deliveryTag, false, true);
                return;
            }
            channel.basicAck(deliveryTag, false);
            rabbitMqMessage.setStatus("消费失败");
            adapterRabbitMqRedis.resetRabbitmqRedis(rabbitMqMessage);

            // 超过重试次数，做相关处理（比如保存数据库等操作），如果抛出异常，则会自动进入死信队列
            log.error("消息消费失败, {}:{}", rabbitMqMessage.getRandomNumber(), rabbitMqMessage.getContent());
            log.error("超过最大重试次数：" + RabbitMqCommonProperties.X_RETRIES_TOTAL);
        }  catch (JedisException ex) {
            try {
                channel.basicAck(deliveryTag, false);
            } catch (IOException e1) {
                log.error("JedisException消息队列失败:" , ex);
            }
            log.error("JedisException消息队列失败:" , ex);
        } catch (Exception ex) {
            try {
                RabbitMqMessage rabbitMqMessage = message.getPayload();
                channel.basicAck(deliveryTag, false);
                rabbitMqMessage.setStatus("消费失败");
                adapterRabbitMqRedis.resetRabbitmqRedis(rabbitMqMessage);
                log.error("Exception消息消费失败," + rabbitMqMessage.getRandomNumber() + ":" + rabbitMqMessage.getContent());
            } catch (IOException e1) {
                log.error("Exception消息队列失败:" , ex);
            }
            log.error("Exception消息队列失败:" , ex);
        }
//        catch (Exception ex) {
//            log.error("消息异常重发处理失败: {}", ex.getMessage(), ex);
//        }
    }

//    @StreamListener("errorChannel")
//    public void handleError(@Payload Message<ErrorMessage> errorMessage) {
//        log.info("{}", errorMessage.getPayload());
//        log.error("发生异常. errorMessage = {}", errorMessage);
//    }
}
