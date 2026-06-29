package com.smc.smccloud.service;

import com.smc.smccloud.model.order.OrderNoInfo;

public interface PurchaseConvertService {

	// 13606 优化解析采购单号,从采购表进行匹配
	public OrderNoInfo convertPoNoFormPurchase(String no, String item);
}
