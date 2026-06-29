package com.smc.smccloud.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Author: B90034
 * Date: 2022-02-14 16:11
 * Description: 接受管道绑定器
 */
public interface InputChannelBinding {


//    String SMS_TO_ERP_RECEIVER = "sms-to-erp-receiver";
//
//    @Input(InputChannelBinding.SMS_TO_ERP_RECEIVER)
//    SubscribableChannel smsToERPReceiver();

    // 订单新建队列
//    String SMS_TO_ERP_ORDER_RECEIVER = "sms-to-erp-order-receiver";
//
//    @Input(InputChannelBinding.SMS_TO_ERP_ORDER_RECEIVER)
//    SubscribableChannel smsToErpOrderReceiver();


    String SMS_TO_OPS_ORDER_CREATE_RECEIVER = "sms-to-ops-order-create-receiver";
    String SMS_TO_OPS_ORDER_EDIT_RECEIVER = "sms-to-ops-order-edit-receiver";
    String SMS_TO_OPS_ORDER_CANCEL_RECEIVER = "sms-to-ops-order-cancel-receiver";
    String SMS_TO_OPS_YYQB_RECEIVER = "sms-to-ops-yyqb-receiver";

    @Input(InputChannelBinding.SMS_TO_OPS_ORDER_CREATE_RECEIVER)
    SubscribableChannel smsToOpsOrderCreateReceiver();

    @Input(InputChannelBinding.SMS_TO_OPS_ORDER_EDIT_RECEIVER)
    SubscribableChannel smsToOpsOrderEditReceiver();

    @Input(InputChannelBinding.SMS_TO_OPS_ORDER_CANCEL_RECEIVER)
    SubscribableChannel smsToOpsOrderCancelReceiver();

    @Input(InputChannelBinding.SMS_TO_OPS_YYQB_RECEIVER)
    SubscribableChannel smsToOpsYyqbReceiver();
}
