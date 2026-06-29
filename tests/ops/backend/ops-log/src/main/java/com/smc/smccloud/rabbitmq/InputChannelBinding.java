package com.smc.smccloud.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;


/**
 * 接收消息管道绑定
 */
public interface InputChannelBinding {


    /**
     * API操作日志
     */
    String OPS_API_LOG_RECEIVER = "ops-api-log-receiver";

    @Input(InputChannelBinding.OPS_API_LOG_RECEIVER)
    SubscribableChannel apiLogReceiver();


    /**
     * system 全局日志记录
     */
    String OPS_COMMON_LOG_RECEIVER = "ops-common-log-receiver";
    @Input(InputChannelBinding.OPS_COMMON_LOG_RECEIVER)
    SubscribableChannel commonLogReceiver();





}
