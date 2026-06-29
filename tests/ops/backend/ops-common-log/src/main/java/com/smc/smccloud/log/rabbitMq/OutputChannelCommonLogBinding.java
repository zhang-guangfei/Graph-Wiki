package com.smc.smccloud.log.rabbitMq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Author lyc
 * @Date 2023/5/29 9:49
 * @Descripton TODO
 */
public interface OutputChannelCommonLogBinding {

    /**
     * 全局操作日志消息
     */
    String OPS_COMMON_LOG_SENDER = "ops-common-log-sender";


    @Output(OutputChannelCommonLogBinding.OPS_COMMON_LOG_SENDER)
    MessageChannel toCommonLog();
}
