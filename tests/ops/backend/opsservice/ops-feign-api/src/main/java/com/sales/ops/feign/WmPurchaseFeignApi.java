package com.sales.ops.feign;

import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "wm-service", contextId = "PurchaseApi")
public interface WmPurchaseFeignApi {

	/**
	 * bugid: 17646 c14717 20250526
	 * @return
	 */
	@RequestMapping(value = "/wmPurchase/interceptPoAutoInv", method = RequestMethod.GET)
	public CommonResult<String> interceptPoAutoInv();

	@RequestMapping(value = "/wmPurchase/updateInvoice", method = RequestMethod.POST)
	public CommonResult<String> updateInvoice(@RequestBody List<PurchaseReplyInfo> list);

	@RequestMapping(value = "/wmPurchase/impdata", method = RequestMethod.POST)
	public CommonResult<String> impdata(@RequestParam("invoiceNo") String invoiceNo,
			@RequestParam("invoiceId") long invoiceId);

	// 补充缺失采购单
	@RequestMapping(value = "/wmPurchase/addDeletePurchase", method = RequestMethod.POST)
	public CommonResult<String> addDeletePurchase(@RequestParam("invoiceNo") String invoiceNo,
			@RequestParam("invoiceId") long invoiceId);


	@RequestMapping(value = "/wmPurchase/pushPurchaseInvoiceInfoToPSI", method = RequestMethod.GET)
	public CommonResult<String> pushPurchaseInvoiceInfoToPSI();
}
