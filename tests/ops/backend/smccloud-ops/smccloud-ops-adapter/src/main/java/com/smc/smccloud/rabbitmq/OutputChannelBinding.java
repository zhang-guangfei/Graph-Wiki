package com.smc.smccloud.rabbitmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Author: B90034
 * Date: 2022-02-14 16:13
 * Description: 发送管道绑定器
 */
public interface OutputChannelBinding {

    String OPS_TO_SMS_ORDER_CREATE_SENDER = "ops-to-sms-order-create-sender";
    String OPS_TO_SMS_ORDER_EDIT_SENDER = "ops-to-sms-order-edit-sender";
    String OPS_TO_SMS_ORDER_CANCEL_SENDER = "ops-to-sms-order-cancel-sender";
    String OPS_TO_SMS_YYQB_SENDER = "ops-to-sms-yyqb-sender";
    //String ERP_TO_SMS_SENDER = "erp-to-sms-sender";
    //String SMS_TO_OPS_ORDER_CREATE_SENDER = "sms-to-ops-order-create-sender";
    /**
     * 订单接单消息
     */
    String OPS_ORDER_RECEIVE_SENDER = "ops-order-receive-sender";

    /**
     * 订单状态变更消息
     */
    String OPS_ORDER_STATE_SENDER = "ops-order-state-sender"; // 配置在 yml文件里

    /**
     * 订单操作日志消息
     */
    String OPS_ORDER_LOG_SENDER = "ops-order-log-sender";

    @Output(OutputChannelBinding.OPS_TO_SMS_ORDER_CREATE_SENDER)
    MessageChannel opsToSmsOrderCreate();

    @Output(OutputChannelBinding.OPS_TO_SMS_ORDER_EDIT_SENDER)
    MessageChannel opsToSmsOrderEdit();

    @Output(OutputChannelBinding.OPS_TO_SMS_ORDER_CANCEL_SENDER)
    MessageChannel opsToSmsOrderCancel();

    @Output(OutputChannelBinding.OPS_TO_SMS_YYQB_SENDER)
    MessageChannel opsToSmsYyqb();

//    @Output(OutputChannelBinding.ERP_TO_SMS_SENDER)
//    MessageChannel erpToSms();

    /*@Output(OutputChannelBinding.SMS_TO_OPS_ORDER_CREATE_SENDER)
    MessageChannel smsToOpsOrderCreate();*/

    @Output(OutputChannelBinding.OPS_ORDER_RECEIVE_SENDER)
    MessageChannel toOrderRccevie();

    @Output(OutputChannelBinding.OPS_ORDER_STATE_SENDER)
    MessageChannel toOrderState();

    @Output(OutputChannelBinding.OPS_ORDER_LOG_SENDER)
    MessageChannel toOrderLog();
}
