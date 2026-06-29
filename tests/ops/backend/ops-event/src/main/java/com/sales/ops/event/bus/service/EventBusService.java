package com.sales.ops.event.bus.service;


import com.sales.ops.event.repository.entity.OrderEvent;

import java.util.List;

public interface EventBusService {



    void preprocess(List<OrderEvent> events);
}
