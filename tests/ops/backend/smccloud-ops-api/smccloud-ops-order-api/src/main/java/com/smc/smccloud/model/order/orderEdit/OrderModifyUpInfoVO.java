package com.smc.smccloud.model.order.orderEdit;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/8/8 13:07
 * @Descripton TODO
 */
@Data
public class OrderModifyUpInfoVO {
    // 业务编码
    private String businessCode;

    private String orderFno;
    private String orderNo;
    private String orderItem;

    private String modelNo;

    private String qty;

    private String eprice;
    // 含税单价
    private String unitPrice;
    // 客户PO号
    private String purchaseNo;
    // 客户物料号
    private String cproductNo;
    // 产地
    private String supplier;
    //开票方式
    private String invoiceWay;
    // 开票类型
    private String invoiceType;
    // 是否可变更
    private Boolean isReset;

    private String customerNo;

    private String userNo;
    // 不可变更的原因
    private String remark;

    private int status;

    private String statusName;

    // 废弃
    private int stateCode;

    private Boolean intercept;
    // 废弃
    private int prodFlag;

    private int expQty;
}
