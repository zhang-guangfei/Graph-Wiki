package com.sales.ops.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.purchase.RequestPurchasePreService;

@CrossOrigin
@RestController
@RequestMapping("/requestPurchasePre")
public class RequestPurchasePreController {

	private static Logger log = LoggerFactory.getLogger(RequestPurchasePreController.class);

	@Resource
	private RequestPurchasePreService requestPurchasePreService;

	/***
	 * 请购单预处理 状态、属性填充等
	 */
	@RequestMapping(value = "/pre")
	public CommonResult pre() {
		int a = 0;
		try {
			a = requestPurchasePreService.updateRequestPurchase();
		} catch (Exception e) {
			// bug14263增加错误返回堆栈信息打印到log
			log.error("pre,error:", e);
			// bug14634 若报错则抛出异常
			throw e;
		}
		return CommonResult.success(a);
	}

	@RequestMapping(value = "/preBin")
	public CommonResult preBin() {
		int a = requestPurchasePreService.updateRequestPurchaseBin();
		return CommonResult.success(a);
	}
}
