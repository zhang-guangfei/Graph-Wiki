package com.sales.ops.service.impinvoice;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

/**
 * @author B91717
 * 交付子系统发票信息导入
 */
public interface ImpInvoiceSysnService {


    /**
     * 定时抽取发票信息
     * 从集团采购系统gps库中获取发票信息
     */
    ResultVo<String> impInvoiceSysn();


    ResultVo<String> copyToInvoicedetailPack(Long invoiceId);


    // 检查发票差异
    ResultVo<Integer> checkImpInvoiceError(Long invoiceId);

}
