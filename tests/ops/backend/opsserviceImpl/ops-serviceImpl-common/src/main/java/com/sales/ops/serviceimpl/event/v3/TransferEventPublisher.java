package com.sales.ops.serviceimpl.event.v3;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.event.publisher.EventPublisher;
import com.sales.ops.event.publisher.entity.CreateInfoDto;
import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferEventPublisher {

    @Autowired
    private EventPublisher eventPublisher;

    public void transferOrderEvent(EventSourceEnum eventSource, OpsDo opsDo) {
        OrderNoInfo orderNo = new OrderNoInfo(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
        eventPublisher.publish(eventSource, orderNo, opsDo, new CreateInfoDto("wm"));
    }
    public void transferOrderEvent(EventSourceEnum eventSource, OpsDo opsDo, Object params) {
        OrderNoInfo orderNo = new OrderNoInfo(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
        eventPublisher.publish(eventSource, orderNo, params, new CreateInfoDto("wm"));
    }

    public void transferOrderEvent(EventSourceEnum eventSource, OpsRo opsRo) {
        OrderNoInfo orderNo = new OrderNoInfo(opsRo.getOrderId(), opsRo.getOrderItem(), opsRo.getNum());
        eventPublisher.publish(eventSource, orderNo, opsRo, new CreateInfoDto("wm"));
    }

    public void transferOrderEvent(EventSourceEnum eventSource, OpsRo opsRo, Object params) {
        OrderNoInfo orderNo = new OrderNoInfo(opsRo.getOrderId(), opsRo.getOrderItem(), opsRo.getNum());
        eventPublisher.publish(eventSource, orderNo, params, new CreateInfoDto("wm"));
    }

}
