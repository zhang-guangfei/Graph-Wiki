package com.smc.smccloud.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;


/**
 * 接收消息管道绑定
 */
public interface InputChannelBinding {

    // 导入库存供应商
    String INVENTORY_SUPPLIER_RECEIVER = "inventory-supplier-receiver";

    @Input(InputChannelBinding.INVENTORY_SUPPLIER_RECEIVER)
    SubscribableChannel inventorySupplierReceiver();
}
