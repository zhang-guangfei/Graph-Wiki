package com.sales.ops.serviceimpl.event.v3;

import com.sales.ops.db.entity.ImpInvoiceDetail;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.purchase.PoReplyInfoDto;
import com.sales.ops.dto.purchase.PurchaseUpdateInfo;
import com.sales.ops.event.publisher.EventPublisher;
import com.sales.ops.event.publisher.entity.CreateInfoDto;
import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseEventPublisher {

    @Autowired
    private EventPublisher eventPublisher;

    //请购预处理、拦截、放行
    public void requestPurchaseOrderEvent(EventSourceEnum eventSource, OpsRequestpurchase request) {
        OrderNoInfo orderNo = new OrderNoInfo(request.getOrderno(), request.getItemno(), request.getSplititemno());
        eventPublisher.publish(eventSource, orderNo, request, new CreateInfoDto("po"));
    }

    // 采购发单、接单、接单异常、删单
    public void purchaseOrderEvent(EventSourceEnum eventSource, OpsPurchaseorder purchase, Object obj) {
        OrderNoInfo orderNo = new OrderNoInfo(purchase.getOrderno(), purchase.getItemno(), purchase.getSplititemno());
        eventPublisher.publish(eventSource, orderNo, obj, new CreateInfoDto("po"));
    }

    //返信延期
    public void purchaseOrderEvent(EventSourceEnum eventSource, PoReplyInfoDto replyInfoDto) {
        OrderNoInfo orderNo = new OrderNoInfo(replyInfoDto.getPoOrderNo(), replyInfoDto.getPoItemNo(), replyInfoDto.getPoSplitItemNo());
        eventPublisher.publish(eventSource, orderNo, replyInfoDto, new CreateInfoDto("po"));
    }

    //采购预到货、发票确认
    public void purchaseOrderEvent(EventSourceEnum eventSource, OpsPurchaseinvoice purchaseInvoice) {
        OrderNoInfo orderNo = new OrderNoInfo(purchaseInvoice.getOrderno(), purchaseInvoice.getItemno(), purchaseInvoice.getSplititemno());
        eventPublisher.publish(eventSource, orderNo, purchaseInvoice, new CreateInfoDto("po"));
    }

    // 更新（变更供应商、返信纳期、报关、运输方式）
    public void purchaseOrderEventByNo(EventSourceEnum eventSource, String orderno, Integer itemno, Integer splitno,
                                       PurchaseUpdateInfo info) {
        OrderNoInfo orderNo = new OrderNoInfo(orderno, itemno, splitno);
        if (EventSourceEnum.PURCHASE_ORDER_UPDATE == eventSource) {
            if (info != null && info.getSupplierid() != null) {
                eventSource = EventSourceEnum.PURCHASE_ORDER_SUPPLIER;
            } else if (info != null && info.getBeginproducedate() != null) {
                eventSource = EventSourceEnum.PURCHASE_ORDER_PRODUCT;
            } else if (info != null && info.getCustomsdate() != null) {
                eventSource = EventSourceEnum.PURCHASE_ORDER_CUSTOMS;
            }
        }
        eventPublisher.publish(eventSource, orderNo, info, new CreateInfoDto("po"));
    }

    //采购删单
    public void purchaseOrderEvent(EventSourceEnum eventSource, OrderNoInfo orderNo, Object obj) {
        eventPublisher.publish(eventSource, orderNo, obj, new CreateInfoDto("po"));
    }

    public void purchaseOrderEvent(EventSourceEnum eventSource, ImpInvoiceDetail impInoviceDetail) {
        OrderNoInfo orderNo = new OrderNoInfo(impInoviceDetail.getOrderNo(), impInoviceDetail.getItemNo(), impInoviceDetail.getSplitItemNo());
        eventPublisher.publish(eventSource, orderNo, impInoviceDetail, new CreateInfoDto("po"));
    }

}
