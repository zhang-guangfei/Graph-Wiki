package com.sales.ops.feign.purchase;

import com.sales.ops.dto.purchase.PurchaseDeleteDto;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * wm模块，校验采购单是否可以删除接口
 */
@FeignClient(name = "wm-service", contextId = "PurchaseDeleteValidate")
public interface PurchaseDeleteValidateFeignApi {


    @RequestMapping(value = "/order/purchase/delete", method = RequestMethod.POST)
    CommonResult purchaseDelete(@RequestBody PurchaseDeleteDto purchaseDeleteDto);



}
