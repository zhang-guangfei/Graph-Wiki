package com.smc.smccloud.model.BuInterface;

import lombok.Data;

@Data
public class BuInvoice {

    private String invNo;

    private BuInvoiceMaster invoice;

    private BuInvoiceDetail[] invoiceDetails;

    private BuDeclareInfo[] declareInfos;
}
