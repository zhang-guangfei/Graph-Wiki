package com.sales.ops.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.util.CommonResult;

@FeignClient(name = "po-service", contextId = "PurchaseDeliver")
public interface PurchaseDeliverFeignApi {

	// 交付系统-供应商采购数据变更
	@RequestMapping(value = "/purchaseDeliver/operateMainLog")
	public CommonResult<Integer> operateMainLog();

	// 查询purchaseinvoice数据
	@RequestMapping(value = "/purchaseDeliver/getPurchaseInvoice")
	public CommonResult<List<OpsPurchaseinvoice>> getPurchaseInvoice(@RequestParam("orderno") String orderno,
			@RequestParam("itemno") Integer itemno, @RequestParam("splitno") Integer splitno);

	@RequestMapping(value = "/purchaseDeliver/getPurchaseInvoiceByPono")
	public CommonResult<List<OpsPurchaseinvoice>> getPurchaseInvoiceByPono(@RequestParam("pono") String pono,
			@RequestParam("lineitem") Integer lineitem);

	@RequestMapping(value = "/purchaseDeliver/insertFactPre")
	void insertFactPre(@RequestBody PurchaseReplyInfo o);
}
