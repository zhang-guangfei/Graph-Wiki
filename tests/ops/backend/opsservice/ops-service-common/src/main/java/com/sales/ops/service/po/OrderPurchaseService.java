package com.sales.ops.service.po;

import com.sales.ops.dto.purchase.PurchaseDayDto;
import com.sales.ops.dto.purchase.PurchaseInvoiceDetailInfo;
import com.sales.ops.dto.purchase.PurchaseOrderDetailInfo;

public interface OrderPurchaseService {

	// bug13639 查询请购、采购单信息
	PurchaseOrderDetailInfo getPurchaseInfo(String orderNo, Integer itemNo, Integer splitNo);

	// bug13639 查询采购发票信息
	PurchaseInvoiceDetailInfo getInvoiceInfo(String invoiceNo, Long invoiceId);

	// bug13685 返回最快生产天数及运输天数
	PurchaseDayDto getPurchaseDay(String orderNo, Integer itemNo, Integer splitNo);
}
