package com.sales.ops.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sales.ops.dto.purchase.ModifyPurchaseDto;
import com.sales.ops.dto.util.CommonResult;

@FeignClient(name = "po-service", contextId = "PurchaseModify")
public interface PurchaseModifyFeignApi {

	// 修改运输方式
	@RequestMapping(value = "/purchaseModify/getPurchaseT", method = RequestMethod.POST)
	CommonResult<List<ModifyPurchaseDto>> getPurchaseT(@RequestBody List<String> list);

	// 修改指定出荷日
	@RequestMapping(value = "/purchaseModify/getPurchaseD", method = RequestMethod.POST)
	CommonResult<List<ModifyPurchaseDto>> getPurchaseD(@RequestBody List<String> list);

}
