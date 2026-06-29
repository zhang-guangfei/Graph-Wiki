package com.smc.smccloud.rabbitmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 发送消息管道绑定
 */
public interface OutputChannelBinding {

    /**
     * 消息生产者的配置  这里可以定义不同的通道 --> 通道名
     */

    String INVENTORY_SUPPLIER_SENDER = "inventory-supplier-sender";

    @Output(OutputChannelBinding.INVENTORY_SUPPLIER_SENDER)
    MessageChannel toInventorySupplier();

}
