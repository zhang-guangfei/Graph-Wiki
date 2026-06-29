package com.sales.ops.service.po;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsPurchaseorderCancelLog;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.OpsRequestpurchaseCancelLog;

/**
 * @author C12961
 * @date 2023/2/16 19:18
 */
public interface BasePoService {
	// 判断是否为补库订单
	boolean isSupplyType(String type);

	// 判断是否为销售订单
	boolean isSalesType(String type);

	boolean is_CR_DR_Type(String type);

	boolean isSalesPurchaseType(String type);

	boolean isSupplyPurchaseType(String type);

	boolean isDifferentWarehouse(OpsRequestpurchase request);

	OpsPurchaseorder getPurchase(String poNo, Integer itemNo, Integer splitNo);

	OpsPurchaseorder getPurchase(Long id);

	OpsRequestpurchase getRequestPurchase(Long id);

	OpsRequestpurchase getRequestPurchase(String repoNo, Integer itemNo, Integer splitNo);

	List<OpsRequestpurchase> getRequestPurchaseByPurchase(String poNo, Integer itemNo, Integer splitNo);

	OpsPurchaseorder getPurchaseByRequestPurchase(String poNo, Integer itemNo, Integer splitNo);

	List<OpsRequestpurchase> getRequestPurchasesByRcvNo(String orderNo, int itemNo);

	@Transactional
	void deletePurchase(OpsPurchaseorder purchase, String operator);

	void deletePurchase(OpsPurchaseorder purchase);

	// bug12344 采购删单新增 删单方法
	@Transactional
	void deletePurchaseByCancelLog(OpsPurchaseorderCancelLog opsPurchaseorderCancelLog);

	@Transactional
	void deleteRequestPurchase(OpsRequestpurchase request, String operator);

	void deleteRequestPurchase(OpsRequestpurchase request);

	// bug12344 采购删单新增 删单方法
	@Transactional
	void deleteRequestPurchaseByCancelLog(OpsRequestpurchaseCancelLog opsRequestpurchaseCancelLog);

	List<OpsPurchaseinvoice> getPurchaseInvoices(OpsPurchaseorder purchase);

	List<OpsPurchaseinvoice> getPurchaseInvoicesByNo(String poNo, Integer itemNo, Integer splitNo);

	int updateRequestPurchaseById(OpsRequestpurchase request);

	int updatePurchaseById(OpsPurchaseorder purchase);

	int updatePurchaseInvoiceById(OpsPurchaseinvoice purchaseInvoice);
}
