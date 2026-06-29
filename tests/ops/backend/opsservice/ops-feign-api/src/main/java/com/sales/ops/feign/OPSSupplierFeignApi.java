package com.sales.ops.feign;

import com.sales.ops.db.entity.Supplier;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/12/21 15:13
 */
@FeignClient(name = "ba-service", contextId = "supplier")
public interface OPSSupplierFeignApi {


    @RequestMapping(value = "/supplier/info",method = RequestMethod.GET)
    CommonResult<Supplier> getSupplierInfo(@RequestParam("id") String id);

    @RequestMapping(value = "/supplier/refresh",method = RequestMethod.GET)
    CommonResult<List<String>> refreshSupplier(@RequestParam("mi") String mi);
}
