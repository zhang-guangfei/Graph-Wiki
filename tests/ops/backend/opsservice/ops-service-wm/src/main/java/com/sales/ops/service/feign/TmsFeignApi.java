package com.sales.ops.service.feign;

import com.sales.ops.dto.tms.TmsTrackingResult;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wm-service", contextId = "tmsRouterService", url = "${tms.url}")
public interface TmsFeignApi {

    @RequestMapping(value = "/order/tracking/listOrderRoute", method = RequestMethod.GET)
    CommonResult<TmsTrackingResult> getTmsRoute(@RequestParam String expressNo);

}
