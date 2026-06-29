package com.sales.ops.feign.dds;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "dds-post-order", contextId = "dds-post-2")
public interface DdsPullPurchaseInfoFromOpsFeignApi {

    @RequestMapping("/psiPurchaseInfo/purchaseOntheway/ops")
    ResultVo<String> pullPsiPurchaseOnthewayFromOps();
}
