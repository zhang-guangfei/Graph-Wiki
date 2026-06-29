package com.sales.ops.feign;

import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author C12961
 * @date 2022/11/7 16:54
 */
@FeignClient(name = "ba-service", contextId = "OpsAdjust")
public interface OpsAdjustFeignApi {

    @GetMapping("/adjust/inventory/qty/diff/create")
    CommonResult createOpsInventoryDiff();

    @GetMapping("/adjust/inventory/qty/adjust/create")
    CommonResult createOpsInventoryAdj(@RequestParam(required = false,name = "num",defaultValue="1") Integer num);



    @GetMapping("/adjust/inventory/qty/adjust/update")
    CommonResult handleOpsInventoryAdj(@RequestParam(required = false,name = "num",defaultValue="1") Integer num);
}
