package com.sales.ops.service.po;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

public interface PurchaseImpInfoToPSIService {

    ResultVo<String> getOpsSupplierByConfig(String code, String codeType);

    ResultVo<String> getAdapterSupplierByConfig(String codeFromOps, String codeType);
}
