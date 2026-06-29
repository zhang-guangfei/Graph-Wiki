package com.sales.ops.event.publisher;

import cn.hutool.json.JSONUtil;
import com.sales.ops.event.publisher.entity.CreateInfoDto;
import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.sales.ops.event.publisher.enums.EventTypeEnum;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventPublisher {


    @Autowired
    private OpsEventScanner opsEventScanner;

    public void publish(EventTypeEnum eventType, OrderNoInfo orderNo, Object params, CreateInfoDto create) {
        OrderEvent bus = createEvent(eventType, orderNo, params, create);
        opsEventScanner.insertEventBus(bus);
        // log.debug("订单{}发送了一条事件{}:{}", orderNo, eventType.getCode(), eventType.getDesc());
    }

    public static OrderEvent createEvent(EventTypeEnum eventType, OrderNoInfo orderNo, Object params, CreateInfoDto create) {
        OrderEvent event = new OrderEvent();
        event.setOrderId(orderNo.getOrderNo());
        event.setOrderItem(orderNo.getItemNo().toString());
        event.setSplitNo(orderNo.getSplitNo());
        event.setEventCode(eventType.getCode());
        event.setDealFlag(0);
        event.setCreTime(create.getCreateTime());
        event.setCreator(create.getCreateUser());
        //如果是json字符串，则直接赋值，如果不是json字符串则转为json字符串
        if (params != null) {
            if (params instanceof String && JSONUtil.isJson((String) params)) {
                event.setParams((String) params);
            } else {
                event.setParams(JSONUtil.toJsonStr(params));
            }
        }
        return event;
    }


}
