//package com.smc.smccloud.rabbitmq.channels.receiver;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
//import com.rabbitmq.client.Channel;
//import com.smc.smccloud.core.model.ResultVo.ResultVo;
//import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
//import com.smc.smccloud.core.rabbitmq.Receive;
//import com.smc.smccloud.core.rabbitmq.constants.Constants;
//import com.smc.smccloud.core.utils.PublicUtil;
//import com.smc.smccloud.mapper.InventorySupplierMapper;
//import com.smc.smccloud.model.inventory.InventorySupplierDO;
//import com.smc.smccloud.rabbitmq.InputChannelBinding;
//import com.smc.smccloud.service.InventorySupplierService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//
//import javax.annotation.Resource;
//
///**
// * @Author lyc
// * @Date 2022/1/25 11:12
// * @Descripton TODO
// */
//@Slf4j
//@EnableBinding(InputChannelBinding.class)
//public class InventorySupplierReceiver extends Receive {
//
//    @Resource
//    private InventorySupplierService inventorySupplierService;
//    @Resource
//    private InventorySupplierMapper inventorySupplierMapper;
//
//    @StreamListener(value = InputChannelBinding.INVENTORY_SUPPLIER_RECEIVER,
//            condition = "headers['x-flag']=='" + Constants.OSP_INVENTORY_SUPPLIER + "'")
//    public void receive(@Payload Message<RabbitMqMessage> message,
//                        @Header(value = AmqpHeaders.CHANNEL) Channel channel,
//                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
//        try {
//            if (PublicUtil.isNotEmpty(message)) {
//                RabbitMqMessage rabbitMqMessage = message.getPayload();
//                String content = rabbitMqMessage.getContent();
//                InventorySupplierDO inventorySupplierDO = JSON.parseObject(content, InventorySupplierDO.class);
//                log.info("Receive inventorySupplierDO msg = {}", inventorySupplierDO.toString());
//                if (PublicUtil.isEmpty(inventorySupplierDO)) {
//                    return;
//                }
//                // 初始数量为0
//                LambdaUpdateWrapper<InventorySupplierDO> updateWrapper = new LambdaUpdateWrapper<>();
//                updateWrapper.eq(InventorySupplierDO::getSupplierId, "JP")
//                .set(InventorySupplierDO::getQuantity, 0);
//                try {
//                    inventorySupplierMapper.update(null, updateWrapper);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//
//                if (PublicUtil.isEmpty(inventorySupplierDO.getModelNo()) || PublicUtil.isEmpty(inventorySupplierDO.getSupplierId())) {
//                    log.error("Receive inventorySupplier error = {}", rabbitMqMessage);
//                    return;
//                }
//
//                log.info("库存供应商数据 -> inventorySupplierDO = {}", inventorySupplierDO);
//                int i = inventorySupplierService.insertOrUpdate(inventorySupplierDO);
//                if (i == 0) {
//                    log.error("保存库存供应商数据失败!");
//                    return;
//                }
//                /**
//                 * 确认一条消息：
//                 * deliveryTag:该消息的index
//                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
//                 */
//                channel.basicAck(deliveryTag, false);
//                log.info("消息消费成功 inventorySupplierDO = {}", rabbitMqMessage.getContent());
//                rabbitMqRedis.consumer(rabbitMqMessage); // 从redis中删除
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            messageHandle(channel, deliveryTag, message, ex);
//        }
//    }
//
//
//}
