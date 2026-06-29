package com.sales.ops.event.exchange.service;


import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class EventExchangeHandler {

    @Autowired
    private OpsEventScanner opsEventScanner;


    public HashMap<String, List<OrderEvent>> handle(OrderEvent event) {
        HashMap<String, List<OrderEvent>> map = new HashMap<>();
        Long id = event.getId();
        // 2.1 修改总线表的事件状态
        int i = opsEventScanner.updateEventPoolToHandled(id, event.getVersion());
        if (i == 1) {
            try {
                event.setModifier(null);
                event.setModifyTime(null);
                event.setRemark(null);
                event.setVersion(event.getVersion());
                event.setDealFlag(0);
                event.setCreTime(new Date());
                List<String> queues = opsEventScanner.selectQueueByEventCode(event.getEventCode());
                for (String queue : queues) {
                    if (map.containsKey(queue)) {
                        List<OrderEvent> orderEvents = map.get(queue);
                        orderEvents.add(event);
                    } else {
                        List<OrderEvent> list = new ArrayList<>();
                        list.add(event);
                        map.put(queue, list);
                    }
                }
            } catch (Exception e) {
                log.error("EventExchange处理失败：{}，{}", event.getId(), e.getMessage());
                opsEventScanner.updateEventPoolToFailure(event.getId(), e.getMessage());
            }
        }
        return map;
    }
}