package com.smc.smccloud.model.invoice;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

/**
 * 查看发票明细
 *
 * @author wsf
 * @version 1.0
 * @date 2021/12/1 11:04
 */
@Data
public class ImpInvoiceDetailRequest extends BaseQuery {

    private Integer invoiceId;
    private String invoiceNo;
    private String modelNo;
    private String orderNo;
    private String barcode;
    private String overseaInvoiceNo;


}
