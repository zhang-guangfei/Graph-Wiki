package com.sales.ops.serviceimpl.event.v3.deliveryDate;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.order.CustomerOrderCancelForDeliveryDateDTO;

public interface DeliveryDateEventService {
    void cancelPurchaseOrder(String cancelSource, OpsPurchaseorder purchaseOrder);

    void requestIntercept(OpsRequestpurchase requestpurchase);

    void customerCancel(CustomerOrderCancelForDeliveryDateDTO cancelForOrderDto);
}
