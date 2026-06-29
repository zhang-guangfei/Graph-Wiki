package com.sales.ops.event.exchange.service;


import com.sales.ops.event.repository.entity.OrderEvent;

import java.util.List;

public interface EventExchangeService {


    void handle(List<OrderEvent> events);

}
