package com.sales.ops.service.purchase;

import java.util.List;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;

/**
 * @author C12961
 * @date 2023/3/22 16:39
 */
public interface PurchaseCreateService {

	void addRequestPurchaseBatch(List<RequestPurchaseInfoDto> list) throws OpsException;

	void addRequestPurchase(List<RequestPurchaseInfoDto> list) throws OpsException;
}
