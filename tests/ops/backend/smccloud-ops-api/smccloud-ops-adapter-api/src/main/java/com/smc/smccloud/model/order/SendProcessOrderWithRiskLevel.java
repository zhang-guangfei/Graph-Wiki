package com.smc.smccloud.model.order;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2024/1/23 13:49
 * @Descripton TODO
 */
@Data
public class SendProcessOrderWithRiskLevel {
    private String orderNo; // 订单号
    private String productRiskLevel; // 产品风险等级
}
