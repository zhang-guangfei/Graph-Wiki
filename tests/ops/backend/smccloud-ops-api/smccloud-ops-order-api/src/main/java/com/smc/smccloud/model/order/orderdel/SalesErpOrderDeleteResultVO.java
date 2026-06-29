package com.smc.smccloud.model.order.orderdel;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/11/30 16:23
 * @Descripton TODO
 */
@Data
public class SalesErpOrderDeleteResultVO {
    // 申请号
    private String applyItemNo;
    // 订单号
    private String orderNo;
    // 处理状态
    private String status;
    // 状态描述(比如失败原因)
    private String statusDescription;
    // 二次审批标识 是否需要二次审批
    private Boolean secondProcess;
}
