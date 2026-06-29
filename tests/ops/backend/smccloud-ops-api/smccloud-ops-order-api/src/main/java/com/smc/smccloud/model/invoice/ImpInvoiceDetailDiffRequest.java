package com.smc.smccloud.model.invoice;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/6 14:36
 */
@Data
public class ImpInvoiceDetailDiffRequest  extends BaseQuery {
    /**
     * id
     */
    private  Integer invoiceId;
}
