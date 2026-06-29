package com.sales.ops.feign;

import com.sales.ops.dto.order.FinishPoListForDto;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 采购完纳feign
 * @author B91717
 */
@FeignClient(name = "po-service", contextId = "PurchaseComplete")
public interface PurchaseFinishFeignApi {

    @RequestMapping(value = "/purchase/finishPoHandel", method = RequestMethod.POST)
    CommonResult finishPoHandel(@RequestBody FinishPoListForDto finishPoListForDto);

}
