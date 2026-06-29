package com.sales.ops.service.purchase;

import java.util.List;

import com.sales.ops.db.entity.OpsPurchaseorder;

public interface MinPriceService {

	public void minPriceOperate(List<OpsPurchaseorder> items);

}
