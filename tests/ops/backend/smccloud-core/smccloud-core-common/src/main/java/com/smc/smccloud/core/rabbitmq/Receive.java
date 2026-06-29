package com.smc.smccloud.core.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;

@Slf4j
public abstract class Receive {

	@Autowired
	public RabbitmqRedis rabbitMqRedis;

	public void messageHandle(Channel channel, long deliveryTag, Message<RabbitMqMessage> message, Exception e) {
		log.error(e.getMessage());
		try {
			RabbitMqMessage rabbitMqMessage = message.getPayload();

			/*
			 * 获取重试次数
			 */
			int retries = rabbitMqRedis.getRetrie(rabbitMqMessage);
	        if (retries < RabbitMqCommonProperties.X_RETRIES_TOTAL){
	        	retries++;
	        	rabbitMqMessage.setRetireNumber(retries);
	        	rabbitMqMessage.setDelaySeconds(RabbitMqCommonProperties.ruleMap.get(retries));
	        	rabbitMqRedis.consumerRedisReset(rabbitMqMessage);
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
	        rabbitMqRedis.resetRabbitmqRedis(RabbitMqConstants.RABBITMQ_REDIS_CONSUMER, RabbitMqConstants.RABBITMQ_REDIS_CONSUMER_FAILURE, rabbitMqMessage);

	        /*
	         * 超过重试次数，做相关处理（比如保存数据库等操作），如果抛出异常，则会自动进入死信队列
	         */
	        log.error("超过最大重试次数: {}" + RabbitMqCommonProperties.X_RETRIES_TOTAL);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	@StreamListener("errorChannel")
	public void handleError(@Payload Message<ErrorMessage> errorMessage) {
		log.info("{}", errorMessage.getPayload());
		log.error("发生异常. errorMessage = {}", errorMessage);
	}
}
