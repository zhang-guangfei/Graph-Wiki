package com.sales.ops.serviceimpl.event.v3;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.event.publisher.EventPublisher;
import com.sales.ops.event.publisher.entity.CreateInfoDto;
import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerEventPublisher {

    @Autowired
    private EventPublisher eventPublisher;

    public void customerOrderEvent(EventSourceEnum eventSource, Rcvdetail rcvdetail) {
        OrderNoInfo orderNo = new OrderNoInfo(rcvdetail.getRorderNo(), rcvdetail.getRorderItem(), null);
        eventPublisher.publish(eventSource, orderNo, null, new CreateInfoDto("wm"));
    }


    public void customerOrderEvent(EventSourceEnum eventSource, String orderId, Integer orderItem) {
        OrderNoInfo orderNo = new OrderNoInfo(orderId, orderItem, null);
        eventPublisher.publish(eventSource, orderNo, null, new CreateInfoDto("wm"));
    }

    public void customerOrderEvent(EventSourceEnum eventSource, String orderId, Integer orderItem, Object params) {
        OrderNoInfo orderNo = new OrderNoInfo(orderId, orderItem, null);
        eventPublisher.publish(eventSource, orderNo, params, new CreateInfoDto("wm"));
    }
    //发票签收，到货确认，入库扫描
    public void customerOrderEvent(EventSourceEnum eventSource, String orderId, Integer orderItem, Integer splitNo, Object params) {
        OrderNoInfo orderNo = new OrderNoInfo(orderId, orderItem, splitNo);
        eventPublisher.publish(eventSource, orderNo, params, new CreateInfoDto("wm"));
    }
    public void customerOrderEvent(EventSourceEnum eventSource, OpsDo opsDo) {
        OrderNoInfo orderNo = new OrderNoInfo(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
        if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            // 如果型号拆分，则代表所有的子型号，为null
            if (!DoSourceEnum.isAssModel(opsDo.getDoSource())) {
                orderNo.setSplitNoNotNull(opsDo.getNum());
            }
        } else if (DoTypeEnum.isDB(opsDo.getDoType())) {// 调拨类型
            // 如果型号拆分，要读取assnum
            if (DoSourceEnum.isAssModel(opsDo.getDoSource())) {
                orderNo.setSplitNoNotNull(opsDo.getAssNum());
            } else {
                orderNo.setSplitNoNotNull(opsDo.getNum());
            }
        } else {// 其他类型
            orderNo.setSplitNoNotNull(opsDo.getNum());
        }
        eventPublisher.publish(eventSource, orderNo, opsDo, new CreateInfoDto("wm"));
    }
    public void customerOrderEvent(EventSourceEnum eventSource, OpsDo opsDo, Object params) {
        OrderNoInfo orderNo = new OrderNoInfo(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
        if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            // 如果型号拆分，则代表所有的子型号，为null
            if (!DoSourceEnum.isAssModel(opsDo.getDoSource())) {
                orderNo.setSplitNoNotNull(opsDo.getNum());
            }
        } else if (DoTypeEnum.isDB(opsDo.getDoType())) {// 调拨类型
            // 如果型号拆分，要读取assnum
            if (DoSourceEnum.isAssModel(opsDo.getDoSource())) {
                orderNo.setSplitNoNotNull(opsDo.getAssNum());
            } else {
                orderNo.setSplitNoNotNull(opsDo.getNum());
            }
        } else {// 其他类型
            orderNo.setSplitNoNotNull(opsDo.getNum());
        }
        eventPublisher.publish(eventSource, orderNo, params, new CreateInfoDto("wm"));
    }


}
