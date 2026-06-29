package com.sales.ops.serviceimpl.event.v3.stockadjust;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.event.publisher.enums.EventTypeEnum;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class StockAdjustEventProcessor {

    private OpsTransferOrderEventService opsTransferOrderEventService;
    @Transactional(rollbackFor = Exception.class)
    public void handle(OrderEvent event) throws OpsException {
        EventTypeEnum EventType = EventSourceEnum.parse(event.getEventCode());
        EventSourceEnum eventSource = (EventSourceEnum) EventType;
        switch (eventSource) {
            case INVENTORY_TRANS_ORDER_WAITING:
                opsTransferOrderEventService.updateForWait(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case INVENTORY_TRANS_ORDER_OUTING:
                opsTransferOrderEventService.updateForOuting(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case INVENTORY_TRANS_ORDER_SHIPPED:
                opsTransferOrderEventService.updateForOuted(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case INVENTORY_TRANS_ORDER_SIGNIN:
                opsTransferOrderEventService.updateForSignIn(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case INVENTORY_TRANS_ORDER_GOODS_CONFIRM:
                opsTransferOrderEventService.updateForConfirm(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case INVENTORY_TRANS_ORDER_RECEIVE_CONFIRM:
                opsTransferOrderEventService.updateForFinish(event.getOrderId(), Integer.valueOf(event.getOrderItem()));
                break;
            case INVENTORY_TRANS_ORDER_DELIVERY_PLAN://调库计划事件
                Map<String, Object> transferPlanParam = JSONUtil.toBean(event.getParams(), Map.class);
                opsTransferOrderEventService.exeStockTransferPlan(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo(), transferPlanParam);
                break;
        }
    }


}
