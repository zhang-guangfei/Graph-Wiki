package com.smc.smccloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：	订单执行DIY时间 handleid
 * @date ：Created in 2023/8/8 10:27
 */
@Component
@ConfigurationProperties(prefix = "handle")
public class XXLJOBProp {

    // 奇数
    private Integer orderhandleidodd;
    // 偶数
    private Integer orderhandleideven;

    public Integer getOrderhandleidodd() {
        return orderhandleidodd;
    }

    public void setOrderhandleidodd(Integer orderhandleidodd) {
        this.orderhandleidodd = orderhandleidodd;
    }

    public Integer getOrderhandleideven() {
        return orderhandleideven;
    }

    public void setOrderhandleideven(Integer orderhandleideven) {
        this.orderhandleideven = orderhandleideven;
    }
}
