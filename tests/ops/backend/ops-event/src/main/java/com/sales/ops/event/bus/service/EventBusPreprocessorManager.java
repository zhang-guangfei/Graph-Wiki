package com.sales.ops.event.bus.service;


import com.sales.ops.event.bus.annotation.EventPreprocessor;
import com.sales.ops.event.publisher.enums.EventTypeEnum;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 预处理器管理器
 */
@Slf4j
@Service
public class EventBusPreprocessorManager {


    @Autowired
    private ApplicationContext applicationContext;

    // eventCode,method,processor
    private Map<String, Map<Method, Object>> preprocessors = new HashMap<>();

    // 初始化加载预处理器Bean
    @PostConstruct
    public void init() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EventPreprocessor.class);
        for (Object bean : beans.values()) {
            // EventPreprocessor classAnnotation = bean.getClass().getAnnotation(EventPreprocessor.class);
            for (Method method : bean.getClass().getMethods()) {
                EventPreprocessor annotation = method.getAnnotation(EventPreprocessor.class);
                if (annotation != null) {
                    EventTypeEnum[] eventCodeEnums = annotation.source();
                    for (EventTypeEnum eventEnum : eventCodeEnums) {
                        String eventCode = eventEnum.getCode().toUpperCase();
                        if (!preprocessors.containsKey(eventCode)) {
                            preprocessors.put(eventCode, new HashMap<>());
                        }
                        preprocessors.get(eventCode).put(method, bean);
                    }
                }
            }
        }
    }


    public List<OrderEvent> preprocessing(OrderEvent bus) {
        List<OrderEvent> list = new ArrayList<>();
        String eventCode = bus.getEventCode().toUpperCase();
        if (preprocessors.containsKey(eventCode)) {
            for (Map.Entry<Method, Object> entry : preprocessors.get(eventCode).entrySet()) {
                Method method = entry.getKey();
                Object bean = entry.getValue();
                try {
                    List<OrderEvent> invoke = (List<OrderEvent>) method.invoke(bean, bus);
                    list.addAll(invoke);
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }
        } else {
            log.info("id：{}，没有相应的预处理器{}", bus.getId(), bus.getEventCode());
        }
        return list;
    }


}
