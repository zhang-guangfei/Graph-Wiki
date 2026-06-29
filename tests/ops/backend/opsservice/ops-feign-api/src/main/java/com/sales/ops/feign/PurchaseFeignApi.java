package com.sales.ops.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.dto.util.CommonResult;

@FeignClient(name = "po-service", contextId = "Purchase")
public interface PurchaseFeignApi {

	// 采购单发送
	@RequestMapping(value = "/purchaseSend/send")
	public CommonResult send();

	// 采购单发送
	@RequestMapping(value = "/purchaseSend/sendManu")
	public CommonResult sendManu();

	// 采购单发送
	@RequestMapping(value = "/purchaseSend/sendGZ")
	public CommonResult sendGZ();

	// CTC发单，更新完成状态
	@RequestMapping(value = "/purchaseSend/ctcFinish")
	public CommonResult ctcFinish();

	// CTC发单，更新删除状态
	@RequestMapping(value = "/purchaseSend/ctcDelete")
	public CommonResult ctcDelete();

	// 请购单预处理
	@RequestMapping(value = "/requestPurchasePre/pre")
	public CommonResult pre();

	@RequestMapping(value = "/requestPurchasePre/preBin")
	public CommonResult preBin();

	// 到货发票导入
	// @RequestMapping(value = "/purchaseApi/impdata", method =
	// RequestMethod.POST)
	// public CommonResult<String> impdata(@RequestParam("invoiceNo") String
	// invoiceNo,
	// @RequestParam("invoiceId") long invoiceId);

	// 采购转订
	@RequestMapping(value = "/purchase/transOrder")
	public CommonResult updateTrans(@RequestBody OpsPurchaseorder opsPurchaseorder);

}
