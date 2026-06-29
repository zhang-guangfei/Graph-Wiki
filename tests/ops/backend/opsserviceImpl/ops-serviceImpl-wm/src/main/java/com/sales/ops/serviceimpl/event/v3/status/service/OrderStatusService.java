package com.sales.ops.serviceimpl.event.v3.status.service;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OrderStatus;
import com.sales.ops.db.entity.OrderStatusItem;
import com.sales.ops.dto.order.OrderStatusViewVO;
import com.sales.ops.serviceimpl.event.v3.order.entity.Order;

import java.util.List;

public interface OrderStatusService {
    void updateStatusToCancel(String orderNo, Integer orderItem);

    List<OrderStatusViewVO> findOrderStatusViewList(String orderNo, Integer orderItem);


    List<OrderStatusItem> findOrderStatusItemList(String orderNo, Integer orderItem);

    OrderStatus getOrderStatusInfoByOrderFno(String orderFno) throws Exception;

    OrderStatus findOrderNotAssModelStatus(String orderNo, Integer orderItem, Integer splitNo);

    OrderStatus findOrderAssModelStatus(String orderNo, Integer orderItem, Integer splitNo);

    void updateOrderStatusTable(Order order, String userDto) throws OpsException;

    void handleOrderStatus(Order order, String userDto) throws OpsException;

    void deleteOrderStatus(String orderId, Integer orderItem);
}
