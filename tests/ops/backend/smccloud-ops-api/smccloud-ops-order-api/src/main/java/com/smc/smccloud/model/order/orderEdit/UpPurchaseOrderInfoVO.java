package com.smc.smccloud.model.order.orderEdit;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/8/15 13:01
 * @Descripton TODO
 */
@Data
public class UpPurchaseOrderInfoVO {

    private String bussinessCode;
    private String orderFno;
    private String modelNo;
    private int qty;
    // 订货日期
    private Date purchaseDate;
    // 估计重量
    private BigDecimal netWeight;
    // 运输方式
    private String transType;
    // 日本返信纳期
    private Date dlvModDate;
    // 指定出荷日
    private Date hopeExportDate;
    // 是否可变更
    private Boolean isReset;
    // 可以运输方式
    private String availableTransType;


}
