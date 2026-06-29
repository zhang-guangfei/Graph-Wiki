package com.smc.smccloud.model.invoice;

import lombok.Data;

@Data
public class ImpInvoiceErrorDTO {

    private Long[] ids;

    private Long invoiceId;

    private String ignoreReason;
}
