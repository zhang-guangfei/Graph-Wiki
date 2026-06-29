package com.sales.ops.dto.purchase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 供应商接单时，PO向WMS反馈接单状态
 * @date 2021/12/9 17:54
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoToWmDto {

	private OpsPurchaseorder purchase;
	private List<OpsRequestpurchase> requests;

	public OpsPurchaseorder getPurchase() {
		return purchase;
	}

	public void setPurchase(OpsPurchaseorder purchase) {
		this.purchase = purchase;
	}

	public List<OpsRequestpurchase> getRequests() {
		return requests;
	}

	public void setRequests(List<OpsRequestpurchase> requests) {
		this.requests = requests;
	}


}
