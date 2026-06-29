package com.smc.smccloud.model.invoice;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

/**
 * @Author edp02 @Date 2021/12/22 17:07
 */
@Data
public class PoInvoiceDetailRequest extends BaseQuery {

    private Long invoiceId;
    private String modelNo;
    private String orderNo;
}
