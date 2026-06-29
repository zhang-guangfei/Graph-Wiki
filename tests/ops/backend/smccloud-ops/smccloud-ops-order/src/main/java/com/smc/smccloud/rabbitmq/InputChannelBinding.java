package com.smc.smccloud.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;


/**
 * 接收消息管道绑定
 */
public interface InputChannelBinding {

    /**
     * 订单状态变更
     */
    String OPS_ORDER_SATE_RECEIVER = "ops-order-state-receiver";

    /**
     * 订单操作日志
     */
    String OPS_ORDER_LOG_RECEIVER = "ops-order-log-receiver";

    /**
     * 订单接单
     */
    String OPS_ORDER_RECEIVE_RECEIVER = "ops-order-receive-receiver";

    /**
     * 发票处理
     */
    String OPS_INVOICE_PROCESS_RECEIVER = "ops-invoice-process-receiver";


    @Input(InputChannelBinding.OPS_ORDER_SATE_RECEIVER)
    SubscribableChannel orderStateReceiver();

    @Input(InputChannelBinding.OPS_ORDER_LOG_RECEIVER)
    SubscribableChannel orderLogReceiver();

    @Input(InputChannelBinding.OPS_ORDER_RECEIVE_RECEIVER)
    SubscribableChannel orderReceiveReceiver();

    @Input(InputChannelBinding.OPS_INVOICE_PROCESS_RECEIVER)
    SubscribableChannel inoviceProcessReceiver();
}
