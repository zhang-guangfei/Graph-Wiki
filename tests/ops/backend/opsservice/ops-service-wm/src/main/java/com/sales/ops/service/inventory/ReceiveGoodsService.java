package com.sales.ops.service.inventory;

import com.sales.ops.db.entity.ImpInvoiceMaster;
import com.sales.ops.db.entity.OpsPoInvoice;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.invoice.ImpInvoiceReceiveDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/7 16:21
 */
public interface ReceiveGoodsService {
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    ImpInvoiceMaster getImpInvoiceMasterById(Long invoiceId);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    ImpInvoiceMaster getLastImpInvoiceMaster(String invoiceNo, String supplierNo);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    OpsPoInvoice getPoInvoiceOne(Long invoiceId);

    @Transactional(rollbackFor = Exception.class)
    ResultVo<String> receiveGoods(ImpInvoiceReceiveDTO dto);
}
