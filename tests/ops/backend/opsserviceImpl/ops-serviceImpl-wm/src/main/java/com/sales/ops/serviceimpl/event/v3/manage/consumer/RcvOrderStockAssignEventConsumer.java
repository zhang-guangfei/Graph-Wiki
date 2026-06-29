package com.sales.ops.serviceimpl.event.v3.manage.consumer;

import com.sales.ops.event.processor.SyncEventConsumerStrategy;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.serviceimpl.event.config.OpsPropertyConfig;
import com.sales.ops.serviceimpl.event.v3.stockassign.OrderStockAssignEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RcvOrderStockAssignEventConsumer extends SyncEventConsumerStrategy {

    @Autowired
    private OrderStockAssignEventProcessor orderStockAssignEventProcessor;
    @Autowired
    private OpsPropertyConfig opsPropertyConfig;
    @Autowired
    private OpsRedissonLock opsRedissonLock;

    public RcvOrderStockAssignEventConsumer() {
        super(OpsEventScanner.EventQueue.StockAssign);
    }

    @Override
    protected void processEvent(OrderEvent event) throws Exception {
        String lockKey = "ops:eventLock:" + event.getOrderId() + "-" + event.getOrderItem();
        boolean lock =  opsRedissonLock.addLock(lockKey,60);
        orderStockAssignEventProcessor.handle(event);
        // 释放锁
        if (lock) {
            opsRedissonLock.releaseLock(lockKey);
        }
    }

    @Override//version累加重试次数
    public Integer getRetryCount() {
        return opsPropertyConfig.getRetryCount();
    }

}
