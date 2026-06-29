package com.sales.ops.serviceimpl.event.v3.manage.consumer;

import com.sales.ops.event.processor.SyncEventConsumerStrategy;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.serviceimpl.event.config.OpsPropertyConfig;
import com.sales.ops.serviceimpl.event.v3.purchase.PurchaseOrderEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpsPurchaseOrderEventConsumer extends SyncEventConsumerStrategy {

    @Autowired
    private PurchaseOrderEventProcessor purchaseOrderEventProcessor;
    @Autowired
    private OpsPropertyConfig opsPropertyConfig;

    public OpsPurchaseOrderEventConsumer() {
        super(OpsEventScanner.EventQueue.PurchaseOrder);
    }

    @Override
    protected void processEvent(OrderEvent event) throws Exception {
        purchaseOrderEventProcessor.handle(event);
    }

    @Override//version累加重试次数
    public Integer getRetryCount() {
        return opsPropertyConfig.getRetryCount();
    }

}
