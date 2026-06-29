package com.smc.smccloud.model.invoice;

import lombok.Data;

/**
 * @author wuweidong
 * @create 2022/12/1 17:13
 * @description
 */
@Data
public class PoInvoiceDTO {
    private  Long  invoiceId;
    private String invoiceNo;
    private String fullOrderNo;
}
