package com.sales.ops.serviceimpl.event.v3.manage.consumer;

import com.sales.ops.event.processor.SyncEventConsumerStrategy;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.serviceimpl.event.config.OpsPropertyConfig;
import com.sales.ops.serviceimpl.event.v3.deliveryDate.DeliveryDateEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpsOrderDeliveryDateConsumer extends SyncEventConsumerStrategy {

    @Autowired
    private DeliveryDateEventProcessor deliveryDateEventProcessor;
    @Autowired
    private OpsPropertyConfig opsPropertyConfig;

    public OpsOrderDeliveryDateConsumer() {
        super(OpsEventScanner.EventQueue.DeliveryDate);
    }

    @Override
    protected void processEvent(OrderEvent event) throws Exception {
        deliveryDateEventProcessor.handle(event);
    }

    @Override//version累加重试次数
    public Integer getRetryCount() {
        return opsPropertyConfig.getRetryCount();
    }

}
