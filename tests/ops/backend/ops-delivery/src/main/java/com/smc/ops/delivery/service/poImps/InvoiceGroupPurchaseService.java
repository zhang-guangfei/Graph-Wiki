package com.smc.ops.delivery.service.poImps;

import com.sales.ops.dto.invoice.OpsPoInvoiceDataDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;

/**
 * @author B91717
 * 交付子系统发票信息导入
 * 查询集团采购相关数据
 */
public interface InvoiceGroupPurchaseService {


    /**
     * 查询集团采购中间库的发票主表数据
     * @param maxId
     * @param pageSize
     * @return
     */
    ResultVo<List<OpsPoInvoiceDataDto>> getDeliveryInvoiceList(Long maxId, Integer pageSize) throws Exception;


}
