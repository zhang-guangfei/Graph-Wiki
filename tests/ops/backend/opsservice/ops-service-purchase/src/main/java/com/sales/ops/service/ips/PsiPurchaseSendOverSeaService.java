package com.sales.ops.service.ips;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.ips.PsiDataType;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;
import java.util.Map;

/**
 * @author B91717
 * 向IPS发采购单时，海外订单的发送
 */
public interface PsiPurchaseSendOverSeaService {


    /**
     * OPS向PSI推送采购订单-海外三国
     * @param supplierNo
     *
     * @return
     * @throws Exception
     */
    public ResultVo<String> writeToExcel(String warehouseid, String supplierNo, List<OpsPurchaseinvoice> list, String fullname,
                                     Integer payment, OpsWarehouse opswarehouse, Map<String, Boolean> resMap,
                                     Map<String, OpsCustomer> customerMap, List<OpsPoAutosendRemarkConfig> configList, PsiDataType psiDataType, String batchno, String pdfPath) throws Exception;


}
