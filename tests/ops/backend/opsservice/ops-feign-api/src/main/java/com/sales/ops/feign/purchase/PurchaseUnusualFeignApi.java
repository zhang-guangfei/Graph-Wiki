package com.sales.ops.feign.purchase;

import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "po-service", contextId = "PurchaseUnusual")
public interface PurchaseUnusualFeignApi {

    @GetMapping("/purchase/unusual/handle/CN")
    CommonResult handleUnusualFromCN(@RequestParam(defaultValue = "1000") Integer num);

    @GetMapping("/purchase/unusual/handle/JP")
    CommonResult handleUnusualFromJP(@RequestParam(defaultValue = "1000") Integer num);


    @GetMapping("/purchase/unusual/create")
    CommonResult create(@RequestParam(defaultValue = "1000") Integer num);

}
