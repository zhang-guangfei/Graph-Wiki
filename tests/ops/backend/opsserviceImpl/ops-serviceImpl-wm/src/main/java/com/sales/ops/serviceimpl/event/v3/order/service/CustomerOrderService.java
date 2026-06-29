package com.sales.ops.serviceimpl.event.v3.order.service;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.serviceimpl.event.v3.order.entity.Order;

public interface CustomerOrderService {
    void refreshRcvdetail(Order order, String userDto) throws OpsException;

    void updateInvoiceInfo(Rcvdetail rcvdetail) throws OpsException;

    int getInvoicedQty(String orderFno);

    int getReturnedQty(String orderFno);
}
