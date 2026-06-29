package com.smc.smccloud.model.invoice;


import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2021/11/30 18:53
 */
@Data
public class ImpInvoiceInfoData {

    private ImpInvoiceMasterDO impInvoiceMaster;
    private List<ImpInvoiceDetailPackDO> impInvoiceDetail;
}
