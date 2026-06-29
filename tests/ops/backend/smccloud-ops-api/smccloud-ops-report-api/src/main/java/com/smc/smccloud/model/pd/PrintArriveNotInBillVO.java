package com.smc.smccloud.model.pd;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/6/23 10:24
 * @Descripton TODO
 */
@Data
public class PrintArriveNotInBillVO {
    private String invoiceNo;
    private String caseNo;
    private String modelNo;
    private String qty;
    private String barcode;
    private String createTime;
}
