package com.smc.smccloud.rabbitmq.channels.receiver;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.Receive;
import com.smc.smccloud.log.mapper.OpsSysLogMapper;
import com.smc.smccloud.log.model.Constants;
import com.smc.smccloud.log.model.OpsSysLogDO;
import com.smc.smccloud.rabbitmq.InputChannelBinding;
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
import java.util.Objects;

/**
 * @Author lyc
 * @Date 2023/5/29 10:19
 * @Descripton TODO
 */
@Slf4j
@EnableBinding(InputChannelBinding.class)
public class CommonLogReceiver extends Receive {

    @Resource
    private OpsSysLogMapper opsSysLogMapper;

    @StreamListener(value = InputChannelBinding.OPS_COMMON_LOG_RECEIVER,
            condition = "headers['x-flag']=='" + Constants.OPS_SYSLOG_CREATE + "'")
    public void receive(@Payload Message<RabbitMqMessage> message,
                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
        OpsSysLogDO opsSysLogDO = null;
        try {
            if (Objects.nonNull(message)) {
                log.info("日志 -> commonLog = {}", JSONUtil.toJsonStr(message));
                RabbitMqMessage rabbitMqMessage = message.getPayload();

                if (StringUtils.isNotBlank(rabbitMqMessage.getContent())) {
                    opsSysLogDO = JSON.parseObject(rabbitMqMessage.getContent(), OpsSysLogDO.class);
                    opsSysLogMapper.insert(opsSysLogDO);
                }
                /*
                 * 确认一条消息：
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
                 */
                channel.basicAck(deliveryTag, false);
            }
        } catch (IOException ex) {
            log.error("[system-common-log日志-消息接收]消息消费异常: {},异常数据: {}", ex.getMessage(), JSONObject.toJSONString(opsSysLogDO));
            //messageHandle(channel, deliveryTag, message, ex);
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
