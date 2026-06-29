package com.sales.ops.feign;

import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 采购拦截自动还原
 */
@FeignClient(name = "po-service", contextId = "RequestRestore")
public interface RequestRestoreFeignApi {

	@RequestMapping(value = "/requestPurchaseRestore/autoRestore")
	public CommonResult autoRestore();


}
