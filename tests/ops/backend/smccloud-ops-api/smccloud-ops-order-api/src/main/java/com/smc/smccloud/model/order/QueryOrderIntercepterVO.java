package com.smc.smccloud.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/8/30 12:08
 * @Descripton TODO
 */
@Data
public class QueryOrderIntercepterVO {

    // 完整订单号
    private String rorderFno;

    // 型号
    private String modelNo;

    // 数量
    private Integer quantity;

    // 含税金额
    private BigDecimal amount;

    // 客户交货期
    private Date dlvDate;

    // 状态编码
    private String status;

    // 状态名称
    private String statusName;

    // 信用拦截标识(不用关心这个)
    private Boolean intercept;

    // 处理状态 1信用拦截  0不拦截
    private Integer handleStatus;
}
