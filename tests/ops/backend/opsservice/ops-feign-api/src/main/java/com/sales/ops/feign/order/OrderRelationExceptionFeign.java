package com.sales.ops.feign.order;

import com.sales.ops.dto.order.OrderRelationExceptionDto;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/10/16 14:36
 */
@FeignClient(name = "wm-service", contextId = "OrderRelationException")
public interface OrderRelationExceptionFeign {

    @RequestMapping(value = "/order/relation/exception", method = RequestMethod.POST)
    CommonResult<List<OrderRelationExceptionDto>> getOrderRelationException(@RequestBody List<String> orderFnoList);

}
