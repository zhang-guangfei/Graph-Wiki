package com.sales.ops.event.processor;

import com.sales.ops.event.repository.entity.OrderEvent;

import java.util.List;

public interface EventConsumerStrategy {
    void handle(List<OrderEvent> orderEvents);
}
