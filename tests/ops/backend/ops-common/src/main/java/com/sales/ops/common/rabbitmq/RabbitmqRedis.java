package com.sales.ops.common.rabbitmq;


import com.sales.ops.common.until.RedisMQManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

@Component
@Slf4j
public class RabbitmqRedis {

	@Resource
	private RedisMQManager redisUtil;

	/**
	 * 待发送
	 */
	public void producer(RabbitMqMessage rabbitMqMessage) {
		String keyPrefix = rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_PRODUCER;
		rabbitMqRandomNumber(keyPrefix, rabbitMqMessage);
		String key = keyPrefix + rabbitMqMessage.getRandomNumber();
		redisUtil.set(key, rabbitMqMessage);
		redisUtil.expire(key, RabbitMqConstants.RABBITMQ_REDIS_EXPIRE);
	}

	/**
	 * 消费成功
	 */
	public void consumer(RabbitMqMessage rabbitMqMessage) {
		String keyPrefix = rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER;
		String key = keyPrefix + rabbitMqMessage.getRandomNumber();
		if(redisUtil.hasKey(key)) {
			redisUtil.del(key);
		}
	}




	/**
	 * 发送成功、发送失败、消费失败
	 */
	public void resetRabbitmqRedis(String oldKeyPrefix, String newKeyPrefix, RabbitMqMessage rabbitMqMessage) {
		String key = rabbitMqMessage.getDataType() + oldKeyPrefix + rabbitMqMessage.getRandomNumber();
		if(redisUtil.hasKey(key)) {
			redisUtil.del(key);
		}
        key = rabbitMqMessage.getDataType() + newKeyPrefix + rabbitMqMessage.getRandomNumber();
    	if(redisUtil.hasKey(key)) {
    		rabbitMqRandomNumber(newKeyPrefix, rabbitMqMessage);
    	}
    	redisUtil.set(key, rabbitMqMessage);
		redisUtil.expire(key, RabbitMqConstants.RABBITMQ_REDIS_EXPIRE);
	}

	/**
	 * 获取重试次数
	 */
	public int getRetrie(RabbitMqMessage rabbitMqMessage) {
		String key = rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER + rabbitMqMessage.getRandomNumber();
		System.out.println("mq失败重试 redis key = " + key);
		int retireNumber = 0;
		if(redisUtil.hasKey(key)) {
			RabbitMqMessage rabbitMqMessageRetrie = (RabbitMqMessage) redisUtil.get(key);
			retireNumber = rabbitMqMessageRetrie.getRetireNumber();
		}
		return retireNumber;
	}

	/**
	 * 待消费，记录重试次数
	 */
	public void consumerRedisReset(RabbitMqMessage rabbitMqMessage) {
		String keyPrefix = rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER;
		String key = keyPrefix + rabbitMqMessage.getRandomNumber();
		if(redisUtil.hasKey(key)) {
			redisUtil.set(key, rabbitMqMessage);
		}
	}

	public synchronized RabbitMqMessage rabbitMqRandomNumber(String keyPrefix, RabbitMqMessage rabbitMqMessage) {
//		String randomNumber = random(RabbitMqConstants.RABBITMQ_RANDOM);
//		String key = keyPrefix + randomNumber;
//		while(redisUtil.hasKey(key)) {
//			randomNumber = random(RabbitMqConstants.RABBITMQ_RANDOM);
//		}
//		rabbitMqMessage.setRandomNumber(randomNumber);
//		return rabbitMqMessage;
        String randomNumber = random(RabbitMqConstants.RABBITMQ_RANDOM);
        String key = keyPrefix + randomNumber;
        log.info("redisManager.hasKey(key) :" + redisUtil.hasKey(key) + ":" + rabbitMqMessage.getDataType() + ":生成随机数：" + randomNumber);
        while(redisUtil.hasKey(key)) {
            randomNumber = random(RabbitMqConstants.RABBITMQ_RANDOM);
            key = keyPrefix + randomNumber;
            log.info("wihle redisManager.hasKey(key) :" + redisUtil.hasKey(key) + ":" + rabbitMqMessage.getDataType() + ":生成随机数：" + randomNumber);
        }
        rabbitMqMessage.setRandomNumber(randomNumber);
        return rabbitMqMessage;
	}

	public String random(int n) {
        if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while(true) {
                int k = ran.nextInt(10);
                if( (bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char)(k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }



	/**
	 * 发送成功
	 */
	public void producerSuccess(RabbitMqMessage rabbitMqMessage) {
		String key = rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_PRODUCER + rabbitMqMessage.getRandomNumber();
		redisUtil.del(key);
		String keyPrefix = RabbitMqConstants.RABBITMQ_REDIS_PREFIX + rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER;
    	key = keyPrefix + rabbitMqMessage.getRandomNumber();
    	if(redisUtil.hasKey(key)) {
    		rabbitMqRandomNumber(keyPrefix, rabbitMqMessage);
    	}
    	redisUtil.set(key, rabbitMqMessage);
    	redisUtil.expire(key, RabbitMqConstants.RABBITMQ_REDIS_EXPIRE);
	}

	/**
	 * 发送失败
	 */
	public void producerFailure(RabbitMqMessage rabbitMqMessage) {
		String key = rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_PRODUCER + rabbitMqMessage.getRandomNumber();
		redisUtil.del(key);
		String keyPrefix = rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_PRODUCER_FAILURE;
        key = keyPrefix + rabbitMqMessage.getRandomNumber();
    	if(redisUtil.hasKey(key)) {
    		rabbitMqRandomNumber(keyPrefix, rabbitMqMessage);
    	}
        redisUtil.set(key, rabbitMqMessage);
        redisUtil.expire(key, RabbitMqConstants.RABBITMQ_REDIS_EXPIRE);
	}

	/**
	 * 消费失败
	 */
	public void consumerFailure(RabbitMqMessage rabbitMqMessage) {
		String key = rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER + rabbitMqMessage.getRandomNumber();
		if(redisUtil.hasKey(key)) {
			redisUtil.del(key);
		}
		String keyPrefix = rabbitMqMessage.getDataType() + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER_FAILURE;
        key = keyPrefix + rabbitMqMessage.getRandomNumber();
    	if(redisUtil.hasKey(key)) {
    		rabbitMqRandomNumber(keyPrefix, rabbitMqMessage);
    	}
    	redisUtil.set(key, rabbitMqMessage);
		redisUtil.expire(key, RabbitMqConstants.RABBITMQ_REDIS_EXPIRE);
	}
}
