package com.sales.ops.service.po;

import com.smc.smccloud.model.order.OrderNoInfo;

public interface PurchaseConvertService {

    OrderNoInfo convertPoNoFormPurchase(String no, String item);
}
