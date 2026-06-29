package com.sales.ops.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sales.ops.dto.util.CommonResult;

@FeignClient(name = "po-service", contextId = "PurchaseReply")
public interface PurchaseReplyFeignApi {

	// bug13395 增加采购返信延期提醒自动作业
	@RequestMapping(value = "/purchaseReply/poDelayEvent")
	public CommonResult<Integer> poDelayEvent();
}
