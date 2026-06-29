package com.smc.smccloud.model.invoice;

import lombok.Data;

/**
 * @Author edp02 @Date 2022/7/31 21:02
 */
@Data
public class InvoiceNoAndShipDateVO {

    private static final long serialVersionUID = 1L;

    private String invoiceNo;

    private String shipdate;
}
