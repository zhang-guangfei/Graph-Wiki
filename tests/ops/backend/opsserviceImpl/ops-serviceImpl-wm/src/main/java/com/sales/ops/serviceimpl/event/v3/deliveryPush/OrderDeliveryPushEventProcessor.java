package com.sales.ops.serviceimpl.event.v3.deliveryPush;


import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.dto.ips.IpsReceiveDeliveryInfoFromAllVO;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.event.publisher.enums.EventTypeEnum;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class OrderDeliveryPushEventProcessor {


    private OrderDeliveryPushEventService orderDeliveryPushEventService;

    @Transactional(rollbackFor = Exception.class)
    public void handle(OrderEvent event) throws OpsException {
        EventTypeEnum EventType = EventSourceEnum.parse(event.getEventCode());
        EventSourceEnum eventSource = (EventSourceEnum) EventType;
        Object params = null;
        switch (eventSource) {
            case CUSTOMER_ORDER_ALLOT_FAILURE:
                IpsReceiveDeliveryInfoFromAllVO vo = JSONUtil.toBean(event.getParams(), IpsReceiveDeliveryInfoFromAllVO.class);
                orderDeliveryPushEventService.handleAllotFailure(event.getOrderId(), Integer.valueOf(event.getOrderItem()), vo);
                break;
            case CUSTOMER_ORDER_ALLOT_BEFORE:
                IpsReceiveDeliveryInfoFromAllVO voAllotBefore = JSONUtil.toBean(event.getParams(), IpsReceiveDeliveryInfoFromAllVO.class);
                orderDeliveryPushEventService.handleBeforeAllot(event.getOrderId(), Integer.valueOf(event.getOrderItem()), voAllotBefore);
                break;
            case CUSTOMER_ORDER_NOT_ALLOT:
                CommonResult voNotAllot = JSONUtil.toBean(event.getParams(), CommonResult.class);
                orderDeliveryPushEventService.handleNotAllot(event.getOrderId(), Integer.valueOf(event.getOrderItem()), voNotAllot);
                break;
            case CUSTOMER_ORDER_ALLOT_AFTER:
                orderDeliveryPushEventService.handleAfterAllot(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case CUSTOMER_ORDER_DELIVERY_PLAN_AFTER:
                orderDeliveryPushEventService.handleEsDeliveryDate(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case CUSTOMER_ORDER_PRINT_WEIGHT:
                orderDeliveryPushEventService.handleFactDate(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case CUSTOMER_ORDER_CANCEL:
                params = JSONUtil.toBean(event.getParams(), CancelForOrderDto.class);
                orderDeliveryPushEventService.handleOrderCancel(event.getOrderId(), Integer.valueOf(event.getOrderItem()), (CancelForOrderDto) params);
                break;
            case CUSTOMER_ORDER_STATUS_INVOICED:
                orderDeliveryPushEventService.handleInvoiced(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case PURCHASE_INVOICE_CONFIRM:
                params = JSONUtil.toBean(event.getParams(), OpsPurchaseinvoice.class);
                //orderDeliveryPushEventService.handlePoInvoiceConfirm(event.getOrderId(), Integer.valueOf(event.getOrderItem()), (OpsPurchaseinvoice) params, event.getCreator());
            case PURCHASE_INVOICE_SIGNED:
                params = JSONUtil.toBean(event.getParams(), OpsPurchaseinvoice.class);
                //orderDeliveryPushEventService.handlePoInvoiceSign(event.getOrderId(), Integer.valueOf(event.getOrderItem()), (OpsPurchaseinvoice) params, event.getCreator());
        }
    }


}
