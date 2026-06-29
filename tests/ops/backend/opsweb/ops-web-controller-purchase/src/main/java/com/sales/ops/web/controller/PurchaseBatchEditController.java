package com.sales.ops.web.controller;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.inquiry.InquiryQueryPurchaseDto;
import com.sales.ops.dto.purchase.PurchaseModifyApplyInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.purchase.PurchaseBatchEditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * bug11808采购单变更
 * 
 * @author SMC892N
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/purchaseBatchModify")
public class PurchaseBatchEditController {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseBatchEditController.class);

	@Autowired
	private PurchaseBatchEditService purchaseBatchEditService;


	@RequestMapping(value = "/getRequestInfo")
	CommonResult<List<PurchaseModifyApplyInfoDto>> getPurchaseInfo(@RequestBody List<String> ordernos) {
		try {
			List<PurchaseModifyApplyInfoDto> list = purchaseBatchEditService.selectRequestInfo(ordernos);
			return CommonResult.success(list);
		} catch (OpsException e) {
			return CommonResult.failure("获取失败" + e.getMessage());
		}
	}

	@RequestMapping(value = "/getPurchaseOrder")
	CommonResult<List<InquiryQueryPurchaseDto>> getPurchaseOrder(@RequestBody List<String> ordernos) {
		try {
			List<InquiryQueryPurchaseDto> list = purchaseBatchEditService.selectPurchaseOrder(ordernos);
			return CommonResult.success(list);
		} catch (OpsException e) {
			return CommonResult.failure(e.getMessage());
		}
	}
}
