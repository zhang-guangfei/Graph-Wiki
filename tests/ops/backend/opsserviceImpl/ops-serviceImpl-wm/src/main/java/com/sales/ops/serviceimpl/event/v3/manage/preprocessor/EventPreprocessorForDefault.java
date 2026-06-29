package com.sales.ops.serviceimpl.event.v3.manage.preprocessor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.event.bus.annotation.EventPreprocessor;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.po.OrderPurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.sales.ops.event.publisher.enums.EventSourceEnum.*;

@Slf4j
@Service
@EventPreprocessor
public class EventPreprocessorForDefault {


    @Autowired
    private BasePoService basePoService;
    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;
    @Autowired
    private OrderPurchaseService orderPurchaseService;

    @EventPreprocessor(source = {
            CUSTOMER_ORDER_FINISH,
            CUSTOMER_ORDER_CANCEL,
            CUSTOMER_ORDER_ALLOT,
            CUSTOMER_ORDER_REALLOT,
            CUSTOMER_ORDER_ADJUST,
            CUSTOMER_ORDER_REQUEST_PREPROCESS,
            CUSTOMER_ORDER_REQUEST_INTERCEPT,
            CUSTOMER_ORDER_REQUEST_RELEASE,
            CUSTOMER_ORDER_PURCHASE_RESET,
            CUSTOMER_ORDER_PURCHASE_SEND,
            CUSTOMER_ORDER_PURCHASE_RECEIVE,
            CUSTOMER_ORDER_PURCHASE_RECEIVE_ERROR,
            CUSTOMER_ORDER_PURCHASE_SUPPLIER,
            CUSTOMER_ORDER_PURCHASE_PRODUCT,
            CUSTOMER_ORDER_PURCHASE_CUSTOMS,
            CUSTOMER_ORDER_PURCHASE_UPDATE,
            CUSTOMER_ORDER_PURCHASE_INVOICE_IMPORT,
            CUSTOMER_ORDER_PURCHASE_INVOICE_CONFIRM,
            CUSTOMER_ORDER_PURCHASE_CANCEL,
            WAREHOUSE_SIGNIN_CONFIRM,
            WAREHOUSE_GOODS_CONFIRM,
            WAREHOUSE_RECEIVE_CONFIRM,
            WAREHOUSE_DELIVERY_GOODS_READY,
            WAREHOUSE_DELIVERY_COMMAND_DISPATCHED,
            WAREHOUSE_DELIVERY_WAVE_CONFIRM,
            WAREHOUSE_DELIVERY_WAVE_CANCEL,
            WAREHOUSE_DELIVERY_CREDIT_INTERCEPT,
            WAREHOUSE_DELIVERY_CREDIT_RELEASE,
            WAREHOUSE_DELIVERY_OPERATION,
            WAREHOUSE_DELIVERY_FINISHED,
            WAREHOUSE_DELIVERY_HANDOVER,
            TRANSPORT_ROUTE_INFO,
            CUSTOMER_ORDER_RETURN,
            CUSTOMER_ORDER_INVOICE_ISSUED,
            CUSTOMER_ORDER_MODIFY_WAREHOUSE,
            CUSTOMER_ORDER_MODIFY_DELIVERY_TYPE,
            CUSTOMER_ORDER_MODIFY_DELIVERY_CONFIG,
            CUSTOMER_ORDER_MODIFY_NOTIFY_SHIPMENT_PLAN
    })
    public List<OrderEvent> defaultCustomerStatusHandle(OrderEvent event) {
        OrderEvent newEvent = BeanUtil.copyProperties(event, OrderEvent.class);
        return Arrays.asList(newEvent);
    }


    //制造订单推送PSI库
    @EventPreprocessor(source = {
            CUSTOMER_ORDER_ALLOT_FAILURE,
            CUSTOMER_ORDER_ALLOT_BEFORE,
            CUSTOMER_ORDER_NOT_ALLOT,
            CUSTOMER_ORDER_ALLOT_AFTER,
            CUSTOMER_ORDER_DELIVERY_PLAN_AFTER,
            CUSTOMER_ORDER_PRINT_WEIGHT,
            CUSTOMER_ORDER_CARRIER_INFO,
            CUSTOMER_ORDER_IMPDATA,
            CUSTOMER_ORDER_IMPDATA_INVOICE,
            CUSTOMER_ORDER_STATUS_INVOICED,
    })
    public List<OrderEvent> DeliveryPushEventToTransOrder(OrderEvent event) {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByKey(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
        // 1.不是订单号，而是发票号，直接通过
        // 2.还未写入rcvdetail, 直接通过
        if (rcvdetail == null) {
            OrderEvent newEvent = BeanUtil.copyProperties(event, OrderEvent.class);
            return Arrays.asList(newEvent);
        } else {
            //订单类型为集团订单则通过，非集团订单则忽略
            if (rcvdetail.getOrderType() != null) {
                if (OrderTypeEnum.JITUAN.equals(rcvdetail.getOrderType().toString())) {
                    OrderEvent newEvent = BeanUtil.copyProperties(event, OrderEvent.class);
                    return Arrays.asList(newEvent);
                } else {
                    return Collections.emptyList();
                }
            } else {
                //订单类型为空，跳过
                return Collections.emptyList();
            }
        }
    }


    //调库相关事件
    @EventPreprocessor(source = {
            INVENTORY_TRANS_ORDER_WAITING,
            INVENTORY_TRANS_ORDER_OUTING,
            INVENTORY_TRANS_ORDER_SHIPPED,
            INVENTORY_TRANS_ORDER_SIGNIN,
            INVENTORY_TRANS_ORDER_GOODS_CONFIRM,
            INVENTORY_TRANS_ORDER_RECEIVE_CONFIRM,
            INVENTORY_TRANS_ORDER_DELIVERY_PLAN
    })
    public List<OrderEvent> wmOrderEventToTransOrder(OrderEvent event) {
        OrderEvent newEvent = BeanUtil.copyProperties(event, OrderEvent.class);
        return Arrays.asList(newEvent);
    }

    //采购单相关事件
    @EventPreprocessor(source = {
            PURCHASE_ORDER_SEND,
            PURCHASE_ORDER_RECEIVE,
            PURCHASE_ORDER_SUPPLIER,
            PURCHASE_ORDER_PRODUCT,
            PURCHASE_ORDER_REPLY_DATE,
            PURCHASE_ORDER_CUSTOMS,
            PURCHASE_ORDER_UPDATE,
            PURCHASE_ORDER_CANCEL,
            PURCHASE_INVOICE_IMPORT,
            PURCHASE_INVOICE_CONFIRM,
            PURCHASE_INVOICE_SIGNED,
            PURCHASE_INVOICE_GOODS,
            PURCHASE_ORDER_DELAY_DATE,
            REQUEST_INTERCEPT,
            REQUEST_RELEASE,
            PURCHASE_ORDER_RO_CONFIRM
    })
    public List<OrderEvent> purchaseEvent(OrderEvent event) {
        OrderEvent newEvent = BeanUtil.copyProperties(event, OrderEvent.class);
        return Arrays.asList(newEvent);
    }

    //请购单影响客单相关事件
    @EventPreprocessor(source = {
            REQUEST_PREPROCESS,
            REQUEST_INTERCEPT,
            REQUEST_RELEASE,
    })
    public List<OrderEvent> requestPurchaseEventToCustomerOrder(OrderEvent event) {
        // 请购单号==》客户单号
        OpsRequestpurchase repo = basePoService.getRequestPurchase(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo());
        if (repo != null) {
            boolean rcvDetailExist = baseCustomerOrderService.isRcvDetailExist(repo.getOrderno(), repo.getItemno());
            if (rcvDetailExist) {
                OrderEvent newEvent = BeanUtil.copyProperties(event, OrderEvent.class);
                newEvent.setEventCode(EventSourceEnum.toCustomerEvent(EventSourceEnum.parse(event.getEventCode())).getCode());
                OpsRequestpurchase requestPurchase = basePoService.getRequestPurchase(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo());
                newEvent.setParams(JSONUtil.toJsonStr(requestPurchase));
                return Arrays.asList(newEvent);
            }
        }
        return Collections.emptyList();
    }

    // 采购单影响客单相关事件：采购单号=》请购单号=》rcv过滤，param保留(OpsPurchaseorder.class)
    @EventPreprocessor(source = {
            PURCHASE_ORDER_SEND,
            PURCHASE_ORDER_RESET,

    })
    public List<OrderEvent> purchaseSendEventToCustomerOrder(OrderEvent event) {
        List<OrderEvent> newEventList = new ArrayList<>();
        List<OpsRequestpurchase> requestList = basePoService.getRequestPurchaseByPurchase(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo());
        if (!CollectionUtils.isEmpty(requestList)) {
            for (OpsRequestpurchase request : requestList) {
                OrderEvent newEvent = BeanUtil.copyProperties(event, OrderEvent.class);
                boolean rcvDetailExist = baseCustomerOrderService.isRcvDetailExist(request.getOrderno(), request.getItemno());
                if (rcvDetailExist) {
                    newEvent.setOrderId(request.getOrderno());
                    newEvent.setOrderItem(request.getItemno().toString());
                    newEvent.setSplitNo(request.getSplititemno());
                    newEvent.setEventCode(EventSourceEnum.toCustomerEvent(EventSourceEnum.parse(event.getEventCode())).getCode());
                    newEventList.add(newEvent);
                }
            }
            return newEventList;
        }
        return Collections.emptyList();
    }

    // 采购单影响客单相关事件：采购单号==》在途库存==》预约关系==》物流指令==》客户单号
    @EventPreprocessor(source = {
            PURCHASE_ORDER_RECEIVE,
            PURCHASE_ORDER_UPDATE,
            PURCHASE_ORDER_SUPPLIER,
            PURCHASE_ORDER_PRODUCT,
            PURCHASE_ORDER_CUSTOMS,
            PURCHASE_INVOICE_IMPORT,
            PURCHASE_INVOICE_CONFIRM,
            PURCHASE_INVOICE_TRANSFER,
    })
    public List<OrderEvent> purchaseEventToCustomerOrder(OrderEvent event) {
        List<OrderNoInfo> customerOrderNoList = baseCustomerOrderService.getCustomerOrderNoFromPoNo(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo());
        List<OrderEvent> events = customerOrderNoList.stream()
                .map(rcv -> {
                    OrderEvent newEvent = BeanUtil.copyProperties(event, OrderEvent.class);
                    newEvent.setOrderId(rcv.getOrderNo());
                    newEvent.setOrderItem(rcv.getItemNo().toString());
                    newEvent.setSplitNo(rcv.getSplitNoNotNull());
                    newEvent.setEventCode(EventSourceEnum.toCustomerEvent(EventSourceEnum.parse(event.getEventCode())).getCode());
                    OpsPurchaseorder purchase = basePoService.getPurchase(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo());
                    newEvent.setParams(JSONUtil.toJsonStr(purchase));
                    return newEvent;
                })
                .collect(Collectors.toList());
        return events;
    }



}
