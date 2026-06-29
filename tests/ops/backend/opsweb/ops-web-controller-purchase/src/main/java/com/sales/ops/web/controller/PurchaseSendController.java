package com.sales.ops.web.controller;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.purchase.PurchaseExportTxtService;
import com.sales.ops.service.purchase.PurchaseSendOrderGZService;
import com.sales.ops.service.purchase.PurchaseSendOrderManuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/purchaseSend")
public class PurchaseSendController {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseSendController.class);

	@Autowired
	private PurchaseExportTxtService purchaseExportTxtService;

	@Autowired
	private PurchaseSendOrderGZService purchaseSendOrderGZService;

	@Autowired
	private PurchaseSendOrderManuService purchaseSendOrderManuService;

	@RequestMapping(value = "/send")
	public CommonResult send() {
		try {
			String a = purchaseExportTxtService.sendOrder();
			return CommonResult.success(a);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}

	}

	/**
	 * 制造订单发送
	 * @return
	 */
	@RequestMapping(value = "/sendManu")
	public CommonResult sendManu() {
		try {
			purchaseSendOrderManuService.sendOrderToManuInterface();
			return CommonResult.success("制造订单发单成功");
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}

	}

	/**
	 * 广州订单发送
	 * @return
	 */
	@RequestMapping(value = "/sendGZ")
	public CommonResult sendGZ() {
		try {
			purchaseSendOrderGZService.sendOrderGZ();
			return CommonResult.success("广州订单发单成功");
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}

	}


	/**
	 * CTC发单，更新完成状态
	 * @return
	 */
	@RequestMapping(value = "/ctcFinish")
	public CommonResult ctcFinish() {
		try {
			purchaseSendOrderManuService.updateCtcFinish();
			return CommonResult.success("CTC更新完成状态成功");
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}

	}

	/**
	 * CTC发单，更新删除状态
	 * @return
	 */
	@RequestMapping(value = "/ctcDelete")
	public CommonResult ctcDelete() {
		try {
			purchaseSendOrderManuService.updateCtcDel();
			return CommonResult.success("CTC更新删除状态成功");
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}

	}

}
