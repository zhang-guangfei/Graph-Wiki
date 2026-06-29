package com.sales.ops.event.bus.service;


import cn.hutool.core.collection.ListUtil;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EventBusHandler {

    @Autowired
    private OpsEventScanner opsEventScanner;
    @Autowired
    private EventBusPreprocessorManager eventBusPreprocessorManager;

    @Transactional(rollbackFor = Exception.class)
    public void updateHandling(List<OrderEvent> events) {
        for (OrderEvent event : events) {
            opsEventScanner.updateEventBusToHandling(event.getId(), event.getVersion());
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void handleEventBusList(List<OrderEvent> events) {
        //要写入下张表的事件
        List<OrderEvent> eventPoolResult = new ArrayList<>();
        // 执行事件前置处理逻辑
        for (OrderEvent event : events) {
            List<OrderEvent> eventList = handle(event);
            //收集要写入pool的事件
            eventPoolResult.addAll(eventList);
        }
        // 分批批量插入，每批100条，（sql中参数数量要小于2100）
        if (!CollectionUtils.isEmpty(eventPoolResult)) {
            if (eventPoolResult.size() <= 100) {
                opsEventScanner.insertEventPool(eventPoolResult);
            } else {
                List<List<OrderEvent>> partition = ListUtil.partition(eventPoolResult, 100);
                for (List<OrderEvent> list : partition) {
                    opsEventScanner.insertEventPool(list);
                }
            }
        }
        // 将状态处理中的事件更新为已处理
        for (OrderEvent event : events) {
            opsEventScanner.updateEventBusToHandled(event.getId(), event.getVersion());
        }
    }

    public List<OrderEvent> handle(OrderEvent event) {
        List<OrderEvent> events = new ArrayList<>();
        // 通过预处理器，将一条源事件转化为一条或多条预处理后的事件
        List<OrderEvent> preprocessed = eventBusPreprocessorManager.preprocessing(event);
        // 将预处理后的事件插入事件池中
        for (OrderEvent orderEvent : preprocessed) {
            orderEvent.setBusId(event.getId());
            orderEvent.setModifier(null);
            orderEvent.setModifyTime(null);
            orderEvent.setRemark(null);
            orderEvent.setVersion(orderEvent.getVersion());
            orderEvent.setDealFlag(0);
            // 16671改为批量插入
            //opsEventScanner.insertEventPool(orderEvent);
            events.add(orderEvent);
        }
        return events;
    }

}