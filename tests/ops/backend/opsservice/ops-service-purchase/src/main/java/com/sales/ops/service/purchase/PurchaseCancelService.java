package com.sales.ops.service.purchase;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.RequestCancelDto;

import java.util.List;

public interface PurchaseCancelService {

	List<PurchaseInfoForCancel> getPurchase(String orderNo, int itemNo) throws OpsException;

	// bug10700 马腾 查询采购子项方法
	List<PurchaseInfoForCancel> getPurchaseItem(String orderNo, int itemNo, Integer splitNo) throws OpsException;

	boolean cancelPurchase(RequestCancelDto requestCancelDto) throws Exception;

	void purchaseErrorLog(RequestCancelDto requestCancelDto, String errormsg);

}
