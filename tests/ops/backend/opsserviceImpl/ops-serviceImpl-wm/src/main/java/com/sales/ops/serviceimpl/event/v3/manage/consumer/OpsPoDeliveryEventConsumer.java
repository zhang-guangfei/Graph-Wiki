package com.sales.ops.serviceimpl.event.v3.manage.consumer;

import com.sales.ops.event.processor.SyncEventConsumerStrategy;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.serviceimpl.event.config.OpsPropertyConfig;
import com.sales.ops.serviceimpl.event.v3.poDelivery.PoDeliveryEventProcessor;
import com.sales.ops.serviceimpl.event.v3.purchase.PurchaseOrderEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpsPoDeliveryEventConsumer extends SyncEventConsumerStrategy {

    @Autowired
    private PoDeliveryEventProcessor poDeliveryEventProcessor;
    @Autowired
    private OpsPropertyConfig opsPropertyConfig;

    public OpsPoDeliveryEventConsumer() {
        super(OpsEventScanner.EventQueue.PoDelivery);
    }

    @Override
    protected void processEvent(OrderEvent event) throws Exception {
        poDeliveryEventProcessor.handle(event);
    }

    @Override//version累加重试次数
    public Integer getRetryCount() {
        return opsPropertyConfig.getRetryCount();
    }

}
