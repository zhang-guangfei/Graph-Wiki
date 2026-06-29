package com.smc.smccloud.model.invoice;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.List;

@Data
public class ImpInvoiceErrorRequest extends BaseQuery {

    private Integer invoiceId;
    private String modelNo;
    private String orderNo;
    private String ignoreReason;
    private List<String> errorTypeList;
}
