package com.sales.ops.serviceimpl.event.v3.purchase;

import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.purchase.PoReplyInfoDto;
import com.sales.ops.dto.purchase.PurchaseCancelEventParam;
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
public class PurchaseOrderEventProcessor {


    private OpsPurchaseOrderEventService opsPurchaseOrderEventService;

    @Transactional(rollbackFor = Exception.class)
    public void handle(OrderEvent event) throws OpsException {
        EventTypeEnum EventType = EventSourceEnum.parse(event.getEventCode());
        EventSourceEnum eventSource = (EventSourceEnum) EventType;
        switch (eventSource) {
            case PURCHASE_ORDER_CANCEL:
                PurchaseCancelEventParam cancelEventParam = JSONUtil.toBean(event.getParams(), PurchaseCancelEventParam.class);
                opsPurchaseOrderEventService.cancelPurchaseOrder(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo(), cancelEventParam);
                break;
            case PURCHASE_ORDER_REPLY_DATE:
                PoReplyInfoDto replyInfo = JSONUtil.toBean(event.getParams(), PoReplyInfoDto.class);
                opsPurchaseOrderEventService.purchaseReplyDate(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo(), replyInfo);
                break;
            case PURCHASE_ORDER_DELAY_DATE:
                PoReplyInfoDto delayInfo = JSONUtil.toBean(event.getParams(), PoReplyInfoDto.class);
                opsPurchaseOrderEventService.purchaseDelayDate(event.getOrderId(), Integer.valueOf(event.getOrderItem()), event.getSplitNo(), delayInfo);
                break;

        }
    }


}
