package com.sales.ops.web.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.ops.dto.purchase.ModifyPurchaseDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.purchase.PurchaseModifyService;

/**
 * bug11808采购单变更
 * 
 * @author SMC892N
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/purchaseModify")
public class PurchaseModifyController {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseModifyController.class);

	@Autowired
	private PurchaseModifyService purchaseModifyService;

	// 修改运输方式
	@RequestMapping(value = "/getPurchaseT")
	CommonResult<List<ModifyPurchaseDto>> getPurchaseT(@RequestBody List<String> list) {
		try {
			List<ModifyPurchaseDto> info = purchaseModifyService.getPurchase(list, "T");
			return CommonResult.success(info);
		} catch (Exception e) {
			logger.error("PurchaseModifyController--getPurchaseT--error:" + e);
			return CommonResult.failure("获取失败" + e.getMessage());
		}
	}

	// 修改指定出荷日
	@RequestMapping(value = "/getPurchaseD")
	CommonResult<List<ModifyPurchaseDto>> getPurchaseD(@RequestBody List<String> list) {
		try {
			List<ModifyPurchaseDto> info = purchaseModifyService.getPurchase(list, "D");
			return CommonResult.success(info);
		} catch (Exception e) {
			logger.error("PurchaseModifyController--getPurchaseD--error:" + e);
			return CommonResult.failure("获取失败" + e.getMessage());
		}
	}

}
