package com.smc.smccloud.config;

import com.alibaba.fastjson.JSON;
import com.smc.smccloud.core.rabbitmq.RabbitMqConstants;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @Author lyc
 * @Date 2022/3/22 19:35
 * @Descripton TODO
 */
@Slf4j
@Component
public class AdapterRabbitMqRedis {

    @Resource(name = "adapterRedisManager")
    private AdapterRedisManager redisManager;

    private static final Logger logger = LoggerFactory.getLogger(AdapterRabbitMqRedis.class);

    /**
     * 待发送
     */
    public void producer(RabbitMqMessage rabbitMqMessage) {
        String keyPrefix = rabbitMqMessage.getDataType(); // + RabbitMqConstants.RABBITMQ_REDIS_PRODUCER
        rabbitMqRandomNumber(keyPrefix, rabbitMqMessage);
        String key = keyPrefix + rabbitMqMessage.getRandomNumber();
        redisManager.set(key, JSON.toJSONString(rabbitMqMessage), 1296000L);
    }

    /**
     * 消费成功
     */
    public void consumer(RabbitMqMessage rabbitMqMessage) {
        String key = rabbitMqMessage.getDataType() + rabbitMqMessage.getRandomNumber();
        if (redisManager.hasKey(key)) {
            log.info("redis delete key {} {}", key, redisManager.delete(key));
        }
    }

    /**
     * @param applyNo 申请单号
     * @param value   申请单号对应的值
     *                根据申请单号，标识是否成功接收
     */
    public void setApplyNo(String applyNo, Object value) {
        String keyPrefix = RabbitMqConstants.RABBITMQ_REDIS_PREFIX + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER_SUCCESS;
        String key = keyPrefix + applyNo;
        redisManager.set(key, value, 1296000L);
        // redisUtil.expire(key, RabbitMqConstants.RABBITMQ_REDIS_EXPIRE);
    }

    /**
     * @param applyNo 根据申请单号查询是否接收成功
     */
    public Object getApplyNo(String applyNo) {
        String keyPrefix = RabbitMqConstants.RABBITMQ_REDIS_PREFIX + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER_SUCCESS;
        String key = keyPrefix + applyNo;
        if (redisManager.hasKey(key)) {
            return redisManager.get(key);
        }
        return null;
    }

    /**
     * 发送成功、发送失败、消费失败
     */
    public void resetRabbitmqRedis(RabbitMqMessage rabbitMqMessage) {
        String key = rabbitMqMessage.getDataType() + rabbitMqMessage.getRandomNumber();
        if (redisManager.hasKey(key)) {
            redisManager.set(key, JSON.toJSONString(rabbitMqMessage), 1296000L);
        }
    }

    /**
     * 获取重试次数
     */
    public int getRetrie(RabbitMqMessage rabbitMqMessage) {
        String key = rabbitMqMessage.getDataType() + rabbitMqMessage.getRandomNumber();
        int retireNumber = 0;
        if (redisManager.hasKey(key)) {
            Object value = redisManager.get(key);
            RabbitMqMessage rabbitMqMessageRetrie = JSON.parseObject(value.toString(), RabbitMqMessage.class);
            retireNumber = rabbitMqMessageRetrie.getRetireNumber();
        }
        return retireNumber;
    }

    /**
     * 待消费，记录重试次数
     */
    public void consumerRedisReset(RabbitMqMessage rabbitMqMessage) {
        String keyPrefix = rabbitMqMessage.getDataType();
        String key = keyPrefix + rabbitMqMessage.getRandomNumber();
        redisManager.set(key, JSON.toJSONString(rabbitMqMessage), 1296000L);
    }

    public synchronized RabbitMqMessage rabbitMqRandomNumber(String keyPrefix, RabbitMqMessage rabbitMqMessage) {
        String randomNumber = random(RabbitMqConstants.RABBITMQ_RANDOM);
        String key = keyPrefix + randomNumber;
        logger.info("redisManager.hasKey(key) :" + redisManager.hasKey(key) + ":" + rabbitMqMessage.getDataType() + ":生成随机数：" + randomNumber);
        while (redisManager.hasKey(key)) {
            randomNumber = random(RabbitMqConstants.RABBITMQ_RANDOM);
            key = keyPrefix + randomNumber;
            logger.info("wihle redisManager.hasKey(key) :" + redisManager.hasKey(key) + ":" + rabbitMqMessage.getDataType() + ":生成随机数：" + randomNumber);
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
            while (true) {
                int k = ran.nextInt(10);
                if ((bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char) (k + '0');
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
        String key = rabbitMqMessage.getDataType() + rabbitMqMessage.getRandomNumber(); // + RabbitMqConstants.RABBITMQ_REDIS_PRODUCER
        redisManager.delete(key);
        String keyPrefix = RabbitMqConstants.RABBITMQ_REDIS_PREFIX + rabbitMqMessage.getDataType(); // + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER
        key = keyPrefix + rabbitMqMessage.getRandomNumber();
        if (redisManager.hasKey(key)) {
            rabbitMqRandomNumber(keyPrefix, rabbitMqMessage);
        }
        redisManager.set(key, rabbitMqMessage, 1296000L);
    }

    /**
     * 发送失败
     */
    public void producerFailure(RabbitMqMessage rabbitMqMessage) {
        String key = rabbitMqMessage.getDataType() + rabbitMqMessage.getRandomNumber(); // + RabbitMqConstants.RABBITMQ_REDIS_PRODUCER
        redisManager.delete(key);
        String keyPrefix = rabbitMqMessage.getDataType(); // + RabbitMqConstants.RABBITMQ_REDIS_PRODUCER_FAILURE
        key = keyPrefix + rabbitMqMessage.getRandomNumber();
        if (redisManager.hasKey(key)) {
            rabbitMqRandomNumber(keyPrefix, rabbitMqMessage);
        }
        redisManager.set(key, rabbitMqMessage, 1296000L);
    }

    /**
     * 消费失败
     */
    public void consumerFailure(RabbitMqMessage rabbitMqMessage) {
        String key = rabbitMqMessage.getDataType() + rabbitMqMessage.getRandomNumber(); // + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER
        if (redisManager.hasKey(key)) {
            redisManager.delete(key);
        }
        String keyPrefix = rabbitMqMessage.getDataType(); // + RabbitMqConstants.RABBITMQ_REDIS_CONSUMER_FAILURE
        key = keyPrefix + rabbitMqMessage.getRandomNumber();
        if (redisManager.hasKey(key)) {
            rabbitMqRandomNumber(keyPrefix, rabbitMqMessage);
        }
        redisManager.set(key, rabbitMqMessage, 1296000L);
    }
}
