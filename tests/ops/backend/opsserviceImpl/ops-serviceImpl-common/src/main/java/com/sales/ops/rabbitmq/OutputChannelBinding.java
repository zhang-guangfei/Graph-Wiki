package com.sales.ops.rabbitmq;

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
     * ApiLog信息
     */
    String OPS_API_log = "ops-api-log-sender"; // 配置在 yml文件里

    /**
     * 发票处理消息
     */
    String OPS_INVOICE_PROCESS_SENDER = "ops-invoice-process-sender";

    @Output(OutputChannelBinding.OPS_INVOICE_PROCESS_SENDER)
    MessageChannel toInvoiceProcess();


    @Output(OutputChannelBinding.OPS_ORDER_STATE_SENDER)
    MessageChannel toOrderState();


    @Output(OutputChannelBinding.OPS_API_log)
    MessageChannel toApiLog();


    String OPS_COMMON_LOG_SENDER = "ops-common-log-sender";


    @Output(OPS_COMMON_LOG_SENDER)
    MessageChannel toCommonLog();



}
