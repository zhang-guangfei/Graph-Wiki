package com.sales.ops.serviceimpl.event.v3.poDelivery;

import com.sales.ops.common.opsexception.OpsException;
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
public class PoDeliveryEventProcessor {


    private PoDeliveryEventHandler poDeliveryEventHandler;

    @Transactional(rollbackFor = Exception.class)
    public void handle(OrderEvent event) throws OpsException {
        EventTypeEnum EventType = EventSourceEnum.parse(event.getEventCode());
        EventSourceEnum eventSource = (EventSourceEnum) EventType;
        switch (eventSource) {
            //所有的事件类型都用一种处理器
            case PURCHASE_ORDER_SEND:
            case PURCHASE_ORDER_RECEIVE:
            case PURCHASE_ORDER_SUPPLIER:
            case PURCHASE_ORDER_PRODUCT:
            case PURCHASE_ORDER_REPLY_DATE:
            case PURCHASE_ORDER_CUSTOMS:
            case PURCHASE_ORDER_UPDATE:
            case PURCHASE_ORDER_CANCEL:
            case PURCHASE_INVOICE_IMPORT:
            case PURCHASE_INVOICE_CONFIRM:
            case PURCHASE_INVOICE_SIGNED:
            case PURCHASE_INVOICE_GOODS:
            case PURCHASE_ORDER_RO_CONFIRM:
                poDeliveryEventHandler.getDeliverInfoByOrderNo(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo());
                break;
        }
    }


}
