package com.smc.smccloud.rabbitmq.channels.receiver;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.Receive;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.rabbitmq.InputChannelBinding;
import com.smc.smccloud.service.OrderStateService;
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

/**
 * Author: B90034
 * Date: 2022-01-05 10:43
 * Description: 订单状态-消息接收器
 */
@Slf4j
@EnableBinding(InputChannelBinding.class)
public class OrderStateReceiver extends Receive {

    @Resource
    private OrderStateService orderStateService;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private RedisManager redisManager;

    @StreamListener(value = InputChannelBinding.OPS_ORDER_SATE_RECEIVER,
            condition = "headers['x-flag']=='" + Constants.OPS_ORDER_STATE + "'")
    public void receive(@Payload Message<RabbitMqMessage> message,
                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
        OrderStateVO orderStateVO = null;
        String lockKey = null;
        try {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            ResultVo<String> resultVo = null;
            if (PublicUtil.isNotEmpty(message)) {
                RabbitMqMessage rabbitMqMessage = message.getPayload();
                if (StringUtils.isNotBlank(rabbitMqMessage.getContent())) {
                    orderStateVO = JSON.parseObject(rabbitMqMessage.getContent(), OrderStateVO.class);
                    if ( StringUtils.isBlank(orderStateVO.getOrderNo()) && orderStateVO.getPurchaseFlag() != 1) {
                        log.error("订单号为空 : " + JSONObject.toJSONString(orderStateVO));
                    } else if (StringUtils.isNotBlank(orderStateVO.getOrderNo()) && orderStateVO.getOrderNo().length() < 7) {
                        log.error("订单号小于7位 : " + JSONObject.toJSONString(orderStateVO));
                    } else {
                        if (StringUtils.isNotBlank(orderStateVO.getOrderNo())) {
                            lockKey = "ops:rediss:order:state:" + orderStateVO.getOrderNo();
                            redissonUtil.lock(lockKey);
                        }
                        if (orderStateVO.getOrderType() != null && orderStateVO.getStateCode() != null) {
                            if (orderStateVO.getStateCode() == OrderStateEnum.CanceledNotConfirm.code()
                                    && OrderTypeEnum.saleOrder.getCode().equals(String.valueOf(orderStateVO.getOrderType()))) {
                                Thread.sleep(2000); // 取消订单事务隔离会导致订单取消消息处理查不到删单记录,让消息飞一会
                            }
                        }
                        orderStateVO.setMqSendDate(rabbitMqMessage.getDate());
                        resultVo = orderStateService.rcvOrderStateMQ(orderStateVO);
                    }
                }
                /*
                 * 确认一条消息：
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
                 */
                channel.basicAck(deliveryTag, false);
                // rabbitMqRedis.consumer(rabbitMqMessage); // 从redis中删除
                if (resultVo != null && resultVo.isSuccess()) {
                    log.info("[订单状态]消息消费成功处理完毕 {},处理结果{} ", JSON.toJSONString(orderStateVO), JSONUtil.toJsonStr(resultVo));
                } else {
                    log.info("[订单状态]消息消费成功,数据不进行处理 {} {}", JSONUtil.toJsonStr(resultVo), JSON.toJSONString(orderStateVO));
                }

            }
        } catch (Exception ex) {
            log.error("[订单状态-消息接收]消息消费异常: {}, {}",orderStateVO != null ? JSONObject.toJSONString(orderStateVO): "对象为空", ex.getMessage(), ex);
            // messageHandle(channel, deliveryTag, message, ex);
            try {
                // 被拒绝的不重新入列(进死信队列)
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            if (StringUtils.isNotBlank(lockKey)) {
                redissonUtil.unlock(lockKey);
            }
            ThreadLocalMapUtil.remove(GlobalConstant.GRANT_TYPE);
        }
    }
}
