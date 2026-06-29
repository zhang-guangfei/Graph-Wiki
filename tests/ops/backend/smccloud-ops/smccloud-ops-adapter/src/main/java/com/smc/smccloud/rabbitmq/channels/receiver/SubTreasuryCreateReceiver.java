package com.smc.smccloud.rabbitmq.channels.receiver;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.smc.smccloud.config.AdapterReceive;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.adapter.AdapterResult;
import com.smc.smccloud.model.adapter.stock.ChinaRegionWarehouseSupplyApply;
import com.smc.smccloud.rabbitmq.InputChannelBinding;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.StockService;
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
 * 分库申请-消息接收器
 */
@Slf4j
@EnableBinding(InputChannelBinding.class)
public class SubTreasuryCreateReceiver extends AdapterReceive {


    @Resource
    private SendMessage sendMessage;

    @Resource
    private StockService stockService;

    /**
     *先行在库分库补库
     */

    @StreamListener(value = InputChannelBinding.SMS_TO_OPS_YYQB_RECEIVER,
            condition = "headers['x-flag']=='" + Constants.ERP_SUBTREASURY_CREATE + "'")
    public void receive(@Payload Message<RabbitMqMessage> message,
                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {

        try {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            if (message != null) {
                RabbitMqMessage rabbitMqMessage = message.getPayload();
                log.info("分库补库-消息 {}  {}", rabbitMqMessage.getRandomNumber(), rabbitMqMessage.getContent());

                /**
                 * 分库补库接口，如下：分库补库信息写进ERP
                 */
                ChinaRegionWarehouseSupplyApply apply = JSON.parseObject(rabbitMqMessage.getContent(),
                        ChinaRegionWarehouseSupplyApply.class);
                AdapterResult result = stockService.handleSubTreasuryCreateMQ(apply);
                channel.basicAck(deliveryTag, false);
                adapterRabbitMqRedis.consumer(rabbitMqMessage);

                /**
                 *
                 * 分库补库处理结果：将处理结果发送至分库补库接收结果队列 返回信息如下： 分库补库信息：分库补库号，处理状态：success(true、false))
                 * 过滤标记：x-flag = 'erp-subTreasury-create-return'
                 */
                RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(result),
                        Constants.ERP_SUBTREASURY_CREATE_RETURN,
                        Constants.SUBTREASURY,
                        Constants.SMS);
                sendMessage.opsToSmsYyqb(rabbitMqMessageOrder);
                log.info("分库补库-消息消费成功: {} {} ---result: {}", rabbitMqMessage.getRandomNumber(), apply.getId(), result);
            }

        } catch (Exception e) {
            if (StringUtils.isNotBlank(e.getMessage()) && (e.getMessage().startsWith("feign.RetryableException")
                    || e.getMessage().contains("拒绝连接 (Connection refused)")
                    || e.getMessage().contains("Load balancer does not have available server for client"))) {
                log.error("分库补库消费失败: {}", e.getMessage());
                channel.basicNack(deliveryTag, false, true);
            } else {
                log.error("分库补库消费失败: {} ---error: {}", message.getPayload(), e.getMessage(), e);
                messageHandle(channel, deliveryTag, message, e);
            }
        } finally {
            ThreadLocalMapUtil.clear();
        }
    }

}