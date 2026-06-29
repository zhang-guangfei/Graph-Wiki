package com.sales.ops.feign.dds;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dds-post-order", contextId = "dds-post-1")
public interface DdsPullDeliveryInfoFromOpsFeignApi {



    @RequestMapping("/deliveryInfo/pull/gz")
    ResultVo<String> pullGzDeliveryInfoFromOps(@RequestParam(required = false) String expressCompany);

    @RequestMapping("/deliveryInfo/pull/cn")
    ResultVo<String> pullCNDeliveryInfoFromOps();
}
