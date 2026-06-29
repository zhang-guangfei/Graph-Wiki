package com.sales.ops.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.RequestCancelDto;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.purchase.PurchaseCancelService;
import com.sales.ops.service.purchase.PurchaseCreateService;

@CrossOrigin
@RestController
@RequestMapping("/purchaseApi")
public class PurchaseApiController {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseApiController.class);

	@Resource
	private PurchaseCreateService purchaseCreateService;
	@Resource
	private PurchaseCancelService purchaseCancelService;

	@RequestMapping(value = "/addRequest", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<String> addRequest(@RequestBody List<RequestPurchaseInfoDto> list) {
		try {
			purchaseCreateService.addRequestPurchase(list);
		} catch (Exception e) {
			return CommonResult.failure("", e.getMessage());
		}
		return CommonResult.success();
	}

	@RequestMapping(value = "/getPurchase", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<List<PurchaseInfoForCancel>> getPurchase(@RequestParam("orderNo") String orderNo,
			@RequestParam("itemNo") int itemNo) {
		try {
			List<PurchaseInfoForCancel> purchase = purchaseCancelService.getPurchase(orderNo, itemNo);
			return CommonResult.success(purchase);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/getPurchaseItem", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<List<PurchaseInfoForCancel>> getPurchaseItem(@RequestParam("orderNo") String orderNo,
			@RequestParam("itemNo") int itemNo, @RequestParam("splitNo") Integer splitNo) {
		try {
			List<PurchaseInfoForCancel> purchase = purchaseCancelService.getPurchaseItem(orderNo, itemNo, splitNo);
			return CommonResult.success(purchase);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}

	}

	@RequestMapping(value = "/cancelPurchase", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<Boolean> cancelPurchase(@RequestBody RequestCancelDto requestCancelDto) {
		try {
			boolean result = purchaseCancelService.cancelPurchase(requestCancelDto);
			if (result) {
				return CommonResult.success();
			} else {
				return CommonResult.failure();
			}
		} catch (Exception e) {
			logger.error("采购删单失败：" + e);
			// bug 9927,采购删单异常增加异常记录，写入异常表并邮件发送
			purchaseCancelService.purchaseErrorLog(requestCancelDto, e.getMessage());
			return CommonResult.failure("采购删单失败：" + e.getMessage());
		}
	}

}
