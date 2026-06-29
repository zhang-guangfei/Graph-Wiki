package com.smc.smccloud.model.invoice;

import lombok.Data;

import java.util.Date;

/**
 *  入库发票收货信息
 */
@Data
public class ImpInvoiceReceiveDTO {

    private  Long invoiceId;
    /**
     * 发票号
     */
    private  String invoiceNo;
    /**
     * 供应商代码
     */
    private  String supplierNo;
    /**
     * 收货时间
     */
    private Date receiveTime;
    /**
     * 收货仓库
     */
    private String receiveWarehouseCode;

}
