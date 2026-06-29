package com.smc.smccloud.model.invoice;

import lombok.Data;

import java.util.Date;

@Data
public class ImpInvoiceProcessDTO {

    private Long invoiceId;

    private String invoiceNo;

    /**
     * 1-导入发票
     * 2-物流签收
     * 3-发票入库
     * 4-增值税发票导入
     * 5-调整成本金额
     * 6-更新发货金额
     * 7-签收调用关务
     */
    private int processType;

    private Date ReceiveTime;

    private  String remark;

    private Date optTime;
    private String optUser;

}
