package com.smc.smccloud.rabbitmq.channels.receiver;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.Receive;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.invoice.ImpInvoiceProcessDTO;
import com.smc.smccloud.rabbitmq.InputChannelBinding;
import com.smc.smccloud.service.ImpInvoiceService;
import com.smc.smccloud.service.PoInvoiceService;
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
 * Date: 2022-08-01 12:22
 * Description: 发票处理消息接收器
 */
@Slf4j
@EnableBinding(InputChannelBinding.class)
public class InvoiceProcessReceiver extends Receive {

    @Resource
    private ImpInvoiceService impInvoiceService;
    @Resource
    private PoInvoiceService poInvoiceService;

    @StreamListener(value = InputChannelBinding.OPS_INVOICE_PROCESS_RECEIVER,
            condition = "headers['x-flag']=='" + Constants.OPS_INVOICE_PROSECC + "'")
    public void receive(@Payload Message<RabbitMqMessage> message,
                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
        try {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            if (message != null) {
                RabbitMqMessage rabbitMqMessage = message.getPayload();
                log.info("接收发票处理消息: {}", rabbitMqMessage);

                if (StringUtils.isNotBlank(rabbitMqMessage.getContent())) {
                    ImpInvoiceProcessDTO dto = JSON.parseObject(rabbitMqMessage.getContent(), ImpInvoiceProcessDTO.class);
                    ResultVo<String> result;
                    switch (dto.getProcessType()) {
                        case 2://生成成本明细
                            result = impInvoiceService.confirmPOInvoiceDetail(dto.getInvoiceId(), dto.getProcessType());
                            break;
                        case 3://制造的发票入库
                            result = impInvoiceService.produceToconfirmPOInvoice(dto.getInvoiceId());
                            break;
                        case 5:
                            result = poInvoiceService.AsyncAdjustDetailDifferentAmt(dto.getInvoiceId());
                            break;
                        case 6:
                            result = poInvoiceService.updImpShipAmount(dto.getInvoiceId());
                            break;
                        case 7:
                            result = impInvoiceService.toupdateWarehousingByGW(dto);
                            break;
                        case 8://操作完发票入库后检查
                            Thread.sleep(5000);//防止发票入库的事务未提交
                            result = impInvoiceService.finishConfirmImpInvoice(dto);
                             break;
                        default:
                            result = ResultVo.failure("未知的发票处理类型");
                            break;
                    }
                    log.info("执行发票处理消息: {} ----- {}", dto, result);
                }

                channel.basicAck(deliveryTag, false);
                // rabbitMqRedis.consumer(rabbitMqMessage); // 从redis中删除
                log.debug("消息消费成功 {} {}{}", rabbitMqMessage.getFlag(), rabbitMqMessage.getDataType(), rabbitMqMessage.getRandomNumber());
            }
        } catch (Exception e) {
            log.error("消息消费异常: {}", e.getMessage(), e);
            // messageHandle(channel, deliveryTag, message, e);
            try {
                // 被拒绝的不重新入列(进死信队列)
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GRANT_TYPE);
        }
    }
}
