package com.smc.smccloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * Author: B90034
 * Date: 2022-07-19 18:44
 * Description:
 */
@Configuration
@RefreshScope
public class EmailReceiveConfig {

    /**
     * 解析邮件的最大解析天数
     */
    @Value("${email.max-receive-day}")
    private Integer maxReceiveDay;

    public EmailReceiveConfig() {
    }

    public EmailReceiveConfig(int maxReceiveDay) {
        this.maxReceiveDay = maxReceiveDay;
    }

    public Integer getMaxReceiveDay() {
        return Optional.ofNullable(this.maxReceiveDay).orElse(10);
    }

    public void setMaxReceiveDay(Integer maxReceiveDay) {
        this.maxReceiveDay = maxReceiveDay;
    }
}
