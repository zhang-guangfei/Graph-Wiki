package com.sales.ops.serviceimpl.event.v3.status.service;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OrderStatusItem;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.Rcvmaster;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.event.v3.order.entity.Order;
import com.sales.ops.serviceimpl.event.v3.order.service.CustomerOrderService;
import com.sales.ops.serviceimpl.event.v3.order.service.OrderRelationService;
import com.sales.ops.serviceimpl.event.v3.status.enums.OrderStatusItemEnum;
import com.sales.ops.serviceimpl.event.v3.status.enums.RelationErrorCode;
import com.sales.ops.serviceimpl.order.BaseCustomerOrderServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class OrderStatusEventHandler {

    private OrderRelationService orderRelationService;
    private CustomerOrderService customerOrderService;
    private BaseCustomerOrderServiceImpl baseCustomerOrderService;
    private CustomerEventPublisher customerEventPublisher;
    private OrderStatusService orderStatusService;

    public void handleOrderStatusUpdated(String orderNo, Integer orderItem, String userDto) throws OpsException {
        // 订单进入稳定状态后，优先绕过状态表重建，降低对发货明细数据的依赖。
        RefreshStrategy decision = calcStrategyMode(orderNo, orderItem);
        if (decision.mode == RefreshMode.SKIP_ALL) {
            log.info("订单状态事件跳过处理，orderNo={}，orderItem={}，reason={}", orderNo, orderItem, decision.reason);
            return;
        }
        if (decision.mode == RefreshMode.RCV_ONLY) {
            log.info("订单状态事件仅刷新接单表，orderNo={}，orderItem={}，reason={}", orderNo, orderItem, decision.reason);
            Short beforeStatus = decision.rcvdetail.getStatus();
            customerOrderService.updateInvoiceInfo(decision.rcvdetail);
            publishGroupOrderInvoicedEvent(orderNo, orderItem, beforeStatus);
            return;
        }

        // 1.构造订单实体和内部关联关系
        Order order = null;
        try {
            order = orderRelationService.getOrder(orderNo, orderItem);
        } catch (OpsException e) {
            //防止事件早于接单处理
            if (StringUtils.equals(e.getMessage(), RelationErrorCode.NOT_FOUND_JYCK.getDesc())) {
                // 15809如果接单日期在22年8月之前，则查询退货表和发票接口，保留状态明细数据不清空
                Rcvmaster rcvmaster = baseCustomerOrderService.findRcvMaster(orderNo);
                if (rcvmaster.getRorddate() != null && rcvmaster.getRorddate().before(DateUtil.stringToDate("2022-08-12"))) {
                    Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
                    customerOrderService.updateInvoiceInfo(rcvdetail);
                    return;
                } else {
                    // 如果在22年之后，则考虑客单主动还原，且未接单处理，则应删除旧状态,等待客单被处理后的事件
                    orderStatusService.deleteOrderStatus(orderNo, orderItem);
                    return;
                }
            } else {
                throw e;
            }
        }
        // 计算状态表数据
        orderStatusService.handleOrderStatus(order, userDto);
        // 写入数据
        orderStatusService.updateOrderStatusTable(order, userDto);

        try {
            //推送集团订单交付信息
            if (OrderTypeEnum.JITUAN.equals(order.getOrderType())) {
                if (order.getOrderStatusEnum() == RcvOrderStatusEnum.CKING) {
                    //称重打印C
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_PRINT_WEIGHT, order.getOrderId(), order.getOrderItem());
                } else if (order.getOrderStatusEnum() == RcvOrderStatusEnum.INVOICE) {
                    //已开票AB
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_STATUS_INVOICED, order.getOrderId(), order.getOrderItem());
                }
            }
        } catch (Exception e) {
            log.error("推送集团订单交付信息异常", e);
        }
    }

    private RefreshStrategy calcStrategyMode(String orderNo, Integer orderItem) {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByKey(orderNo, orderItem);
        if (rcvdetail == null || rcvdetail.getStatus() == null) {
            return RefreshStrategy.full("未查询到有效接单明细");
        }
        // 保留时间窗口内的订单仍按完整链路刷新状态表。
        if (!isArchiveWindowOrder(orderNo)) {
            return RefreshStrategy.full("订单未超过保留时间窗口");
        }
        // 已退货订单生命周期结束，不再刷新状态表和接单表。
        if (RcvOrderStatusEnum.RETURN.getType() == rcvdetail.getStatus()) {
            return RefreshStrategy.skipAll(rcvdetail, "接单状态已退货，订单生命周期结束");
        }
        // 已开票订单仍可能发生退货，只刷新接单表的开票和退货信息。
        if (RcvOrderStatusEnum.INVOICE.getType() == rcvdetail.getStatus()) {
            return RefreshStrategy.rcvOnly(rcvdetail, "接单状态已开票，仅需刷新退货相关字段");
        }
        // 已发货且运输已签收后，状态表不再依赖运输接口继续变化。
        if (RcvOrderStatusEnum.CKED.getType() == rcvdetail.getStatus() && isTransportDelivered(orderNo, orderItem)) {
            return RefreshStrategy.rcvOnly(rcvdetail, "订单已发货且运输状态已签收，仅需刷新开票/退货字段");
        }
        return RefreshStrategy.full("订单仍需完整刷新状态");
    }

    private boolean isArchiveWindowOrder(String orderNo) {
        Rcvmaster rcvmaster = baseCustomerOrderService.findRcvMaster(orderNo);
        if (rcvmaster == null || rcvmaster.getRorddate() == null) {
            return false;
        }
        // 接单日期早于保留边界时，订单可按历史稳定数据处理。
        Date archiveBoundary = DateUtil.addYear(DateUtil.getCurrentDate(), -1);
        return rcvmaster.getRorddate().before(archiveBoundary);
    }

    private boolean isTransportDelivered(String orderNo, Integer orderItem) {
        List<OrderStatusItem> orderStatusItemList = orderStatusService.findOrderStatusItemList(orderNo, orderItem);
        if (orderStatusItemList == null || orderStatusItemList.isEmpty()) {
            return false;
        }
        // 所有状态明细均已签收时，运输阶段视为结束。
        return orderStatusItemList.stream()
                .map(OrderStatusItem::getStatusDesc)
                .allMatch(statusDesc -> StringUtils.equals(statusDesc, OrderStatusItemEnum.TRANSPORT_DELIVERED.getCode()));
    }

    private void publishGroupOrderInvoicedEvent(String orderNo, Integer orderItem, Short beforeStatus) {
        try {
            Rcvdetail refreshed = baseCustomerOrderService.findRcvDetailByKey(orderNo, orderItem);
            if (refreshed != null
                    && Short.valueOf(OrderTypeEnum.JITUAN).equals(refreshed.getOrderType())
                    && !Short.valueOf(RcvOrderStatusEnum.INVOICE.getType()).equals(beforeStatus)
                    && Short.valueOf(RcvOrderStatusEnum.INVOICE.getType()).equals(refreshed.getStatus())) {
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_STATUS_INVOICED, orderNo, orderItem);
            }
        } catch (Exception e) {
            log.error("推送集团订单开票交付信息异常", e);
        }
    }

    private enum RefreshMode {
        FULL,
        RCV_ONLY,
        SKIP_ALL
    }

    private static class RefreshStrategy {
        private final RefreshMode mode;
        private final Rcvdetail rcvdetail;
        private final String reason;

        private RefreshStrategy(RefreshMode mode, Rcvdetail rcvdetail, String reason) {
            this.mode = mode;
            this.rcvdetail = rcvdetail;
            this.reason = reason;
        }

        private static RefreshStrategy full(String reason) {
            return new RefreshStrategy(RefreshMode.FULL, null, reason);
        }

        private static RefreshStrategy rcvOnly(Rcvdetail rcvdetail, String reason) {
            return new RefreshStrategy(RefreshMode.RCV_ONLY, rcvdetail, reason);
        }

        private static RefreshStrategy skipAll(Rcvdetail rcvdetail, String reason) {
            return new RefreshStrategy(RefreshMode.SKIP_ALL, rcvdetail, reason);
        }
    }

}
