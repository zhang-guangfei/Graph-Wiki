package com.sales.ops.serviceimpl.event.v3.deliveryDate;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.order.CustomerOrderCancelForDeliveryDateDTO;
import com.sales.ops.dto.purchase.PurchaseCancelEventParam;
import com.sales.ops.enums.PurchaseCancelSourceEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.event.publisher.enums.EventTypeEnum;
import com.sales.ops.event.repository.entity.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class DeliveryDateEventProcessor {

    @Autowired
    private DeliveryDateEventService deliveryDateEventService;

    @Transactional(rollbackFor = Exception.class)
    public void handle(OrderEvent event) throws OpsException {
        EventTypeEnum EventType = EventSourceEnum.parse(event.getEventCode());
        EventSourceEnum eventSource = (EventSourceEnum) EventType;
        switch (eventSource) {
            case PURCHASE_ORDER_CANCEL:
                PurchaseCancelEventParam cancelEventParam = JSONUtil.toBean(event.getParams(), PurchaseCancelEventParam.class);
                //purchaseOrder、RequestPurchaseList都可能为空，是否为空取决于PurchaseCancelSourceEnum,-2时为请购删单，其他为采购删单
                OpsPurchaseorder purchaseOrder = new OpsPurchaseorder();
                //请购单删单时，RequestPurchaseList不能为空
                if (PurchaseCancelSourceEnum.CANCEL_REQUEST_PURCHASE.getType().equals(cancelEventParam.getCancelSource()) && !CollectionUtils.isEmpty(cancelEventParam.getRequestpurchaseList())) {
                    OpsRequestpurchase requestpurchase = cancelEventParam.getRequestpurchaseList().get(0);
                    BeanUtil.copyProperties(requestpurchase, purchaseOrder);
                    purchaseOrder.setReceivewarehouseid(requestpurchase.getRequestwarehouseid());
                    purchaseOrder.setHopereceivewarehouse(requestpurchase.getRequestwarehouseid());
                } else if (!PurchaseCancelSourceEnum.CANCEL_REQUEST_PURCHASE.getType().equals(cancelEventParam.getCancelSource()) && cancelEventParam.getPurchaseOrder() != null) {
                    BeanUtil.copyProperties(cancelEventParam.getPurchaseOrder(), purchaseOrder);
                }else {
                    break;
                }
                deliveryDateEventService.cancelPurchaseOrder(cancelEventParam.getCancelSource(), purchaseOrder);
                break;
            case REQUEST_INTERCEPT:
                OpsRequestpurchase requestpurchase = JSONUtil.toBean(event.getParams(), OpsRequestpurchase.class);
                deliveryDateEventService.requestIntercept(requestpurchase);
                break;
            case CUSTOMER_ORDER_CANCEL:
                CustomerOrderCancelForDeliveryDateDTO cancelForOrderDto = JSONUtil.toBean(event.getParams(), CustomerOrderCancelForDeliveryDateDTO.class);
                deliveryDateEventService.customerCancel(cancelForOrderDto);
                break;

        }
    }
}
