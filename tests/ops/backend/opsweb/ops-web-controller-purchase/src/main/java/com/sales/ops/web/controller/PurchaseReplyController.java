package com.sales.ops.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.purchase.PurchaseReplyService;

@CrossOrigin
@RestController
@RequestMapping("/purchaseReply")
public class PurchaseReplyController {

	@Autowired
	private PurchaseReplyService purchaseReplyService;

	@RequestMapping(value = "/poDelayEvent")
	public CommonResult<Integer> poDelayEvent() {
		return CommonResult.success(purchaseReplyService.poDelayEvent());
	}
}
