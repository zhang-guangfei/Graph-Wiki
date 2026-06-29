package com.smc.smccloud.model.order.orderEdit;

import lombok.Data;


/**
 * @Author lyc
 * @Date 2023/9/26 12:22
 * @Descripton TODO
 */
@Data
public class UpPurOrderSupplierVO {
    private String orderno; // 采购单号
    private String supplierid; // 供应商
    private String transtype; // 运输方式
    private String optUser; // 操作人
    private String hopeexportdate; // 指定出荷日
    private String information; // 转订原因
    private String greencode; // 特殊标识

    private String batchNo;
    private String applyNo;
}
