package com.sales.ops.serviceimpl.event.v3.manage.consumer;

import com.sales.ops.event.processor.SyncEventConsumerStrategy;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.serviceimpl.event.config.OpsPropertyConfig;
import com.sales.ops.serviceimpl.event.v3.stockadjust.StockAdjustEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpsStockAdjustEventConsumer extends SyncEventConsumerStrategy {

    @Autowired
    private StockAdjustEventProcessor stockAdjustEventProcessor;
    @Autowired
    private OpsPropertyConfig opsPropertyConfig;

    public OpsStockAdjustEventConsumer() {
        super(OpsEventScanner.EventQueue.StockAdjust);
    }

    @Override
    protected void processEvent(OrderEvent event) throws Exception {
        stockAdjustEventProcessor.handle(event);
    }

    @Override//version累加重试次数
    public Integer getRetryCount() {
        return opsPropertyConfig.getRetryCount();
    }

}
