package com.sales.ops.event.bus.service;


import cn.hutool.core.collection.ListUtil;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class EventBusServiceImpl implements EventBusService {

    @Autowired
    private OpsEventScanner opsEventScanner;
    @Autowired
    private EventBusPreprocessorManager eventBusPreprocessorManager;
    @Autowired
    private EventBusHandler eventBusHandler;
    private static final Integer MAX_RETRY_COUNT = 3;

    @Override
    public void preprocess(List<OrderEvent> events) {
        if (CollectionUtils.isEmpty(events)) {
            return;
        }
        try {
            // 1.修改总线表的事件状态为正在处理中3
            eventBusHandler.updateHandling(events);
            //2.批量处理事件，并开启事务
            eventBusHandler.handleEventBusList(events);
        } catch (Exception e) {
            String message = e.getMessage();
            log.error("总线预处理异常:" + message, e);
            String remark = StringUtils.substring(message, 0, 1000);
            // 3.根据version重试次数，3次失败后更新为处理失败
            Long maxVersion = events.stream().map(OrderEvent::getVersion).filter(Objects::nonNull).max(Comparator.naturalOrder()).orElse(0L);
            if (maxVersion <= MAX_RETRY_COUNT) {
                for (OrderEvent event : events) {
                    opsEventScanner.updateEventBusToInit(event.getId(), remark);
                }
            } else {
                for (OrderEvent event : events) {
                    opsEventScanner.updateEventBusToFailure(event.getId(), remark);
                }
            }
        }
    }


}