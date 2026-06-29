package com.sales.ops.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.RequestCancelDto;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.sales.ops.dto.util.CommonResult;

@FeignClient(name = "po-service", contextId = "RequestPurchase")
public interface RequestPurchaseFeignApi {

	@RequestMapping(value = "/purchaseApi/addRequest", method = RequestMethod.POST)
	CommonResult<String> addRequest(@RequestBody List<RequestPurchaseInfoDto> list);

	@RequestMapping(value = "/purchaseApi/getPurchase", method = RequestMethod.POST)
	CommonResult<List<PurchaseInfoForCancel>> getPurchase(@RequestParam("orderNo") String orderNo,
			@RequestParam("itemNo") int itemNo);

	@RequestMapping(value = "/purchaseApi/cancelPurchase", method = RequestMethod.POST)
	CommonResult<Boolean> cancelPurchase(@RequestBody RequestCancelDto requestCancelDto);
}
