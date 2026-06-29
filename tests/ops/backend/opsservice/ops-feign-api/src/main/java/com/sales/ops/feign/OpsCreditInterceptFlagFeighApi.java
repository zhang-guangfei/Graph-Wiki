package com.sales.ops.feign;

import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "wm-service", contextId = "creditFeighApi")
public interface OpsCreditInterceptFlagFeighApi {

    @PostMapping("/order/rcvorder/credit/interceptFlag")
    CommonResult<Map<String,String>> getCreditInterceptFlagByOrderFno(@RequestBody List<String> orderFnoList);

}
