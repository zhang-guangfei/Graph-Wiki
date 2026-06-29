package com.sales.ops.service.purchase;

public interface PurchaseSendOrderGZService {

	// 工厂订单发送,到广州
	public Integer sendOrderGZ() throws Exception;

	public Integer operateGZ(String versionLike) throws Exception;
}
