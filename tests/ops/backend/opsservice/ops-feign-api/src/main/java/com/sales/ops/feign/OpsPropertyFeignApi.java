package com.sales.ops.feign;

import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author C12961
 * @date 2022/10/19 9:14
 */
@FeignClient(name = "wm-service", contextId = "property")

public interface OpsPropertyFeignApi {


    @RequestMapping("/inventory/property/find")
    CommonResult findProperty(@RequestBody OpsInventoryProperty property);


}
