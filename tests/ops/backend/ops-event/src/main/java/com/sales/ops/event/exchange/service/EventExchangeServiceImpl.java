package com.sales.ops.event.exchange.service;


import cn.hutool.core.collection.ListUtil;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EventExchangeServiceImpl implements EventExchangeService {

    @Autowired
    private OpsEventScanner opsEventScanner;
    @Autowired
    private EventExchangeHandler eventExchangeHandler;

    public void handle(List<OrderEvent> events) {
        HashMap<String, List<OrderEvent>> map = new HashMap<>();
        for (OrderEvent event : events) {
            HashMap<String, List<OrderEvent>> eventMap = eventExchangeHandler.handle(event);
            for (Map.Entry<String, List<OrderEvent>> entry : eventMap.entrySet()) {
                String queueName = entry.getKey();
                List<OrderEvent> eventList = entry.getValue();
                if (!map.containsKey(queueName)) {
                    map.put(queueName, new ArrayList<>());
                }
                List<OrderEvent> orderEvents = map.get(queueName);
                orderEvents.addAll(eventList);
            }
        }
        for (Map.Entry<String, List<OrderEvent>> entry : map.entrySet()) {
            String queueName = entry.getKey();
            List<OrderEvent> eventList = entry.getValue();
            if(!CollectionUtils.isEmpty(eventList)){
                //分批批量插入，每批100条，（sql中参数数量要小于2100）
                if (eventList.size() <= 100) {
                    opsEventScanner.insertEventQueueBatch(eventList, queueName);
                } else {
                    List<List<OrderEvent>> partition = ListUtil.partition(eventList, 100);
                    for (List<OrderEvent> list : partition) {
                        opsEventScanner.insertEventQueueBatch(list, queueName);
                    }
                }
            }
        }
    }


}