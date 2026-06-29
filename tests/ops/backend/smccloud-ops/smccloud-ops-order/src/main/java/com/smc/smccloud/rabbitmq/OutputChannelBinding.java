package com.smc.smccloud.rabbitmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 发送消息管道绑定
 */
public interface OutputChannelBinding {

    /**
     * 订单状态变更消息
     */
    String OPS_ORDER_STATE_SENDER = "ops-order-state-sender"; // 配置在 yml文件里

    /**
     * 订单操作日志消息
     */
    String OPS_ORDER_LOG_SENDER = "ops-order-log-sender";

    /**
     * 订单接单消息
     */
    String OPS_ORDER_RECEIVE_SENDER = "ops-order-receive-sender";

    /**
     * 发票处理消息
     */
    String OPS_INVOICE_PROCESS_SENDER = "ops-invoice-process-sender";


    @Output(OutputChannelBinding.OPS_ORDER_STATE_SENDER)
    MessageChannel toOrderState();

    @Output(OutputChannelBinding.OPS_ORDER_LOG_SENDER)
    MessageChannel toOrderLog();

    @Output(OutputChannelBinding.OPS_ORDER_RECEIVE_SENDER)
    MessageChannel toOrderRccevie();

    @Output(OutputChannelBinding.OPS_INVOICE_PROCESS_SENDER)
    MessageChannel toInvoiceProcess();
}
