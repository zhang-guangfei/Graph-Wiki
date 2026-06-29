package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.invoice.OpsPoInvoiceDO;
import com.smc.smccloud.model.invoice.PoInvoiceDetailDO;

import java.util.Date;
import java.util.List;

public interface SalesImportService {

    ResultVo<String> addSalesImp(List<PoInvoiceDetailDO> detaillist, Date costDate, OpsPoInvoiceDO poMasterDO);

    /**
     * 异步生成组换
     * @param InvoiceId
     */
    void asyncToCreateStockAssembly(Long InvoiceId,String warehouseCode);
}
