package com.sales.ops.serviceimpl.event.v3.manage.consumer;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.event.processor.AsyncEventConsumerStrategy;
import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.serviceimpl.event.config.OpsPropertyConfig;
import com.sales.ops.serviceimpl.event.v3.manage.threadPool.RcvOrderStatusEventAsyncTaskExecutor;
import com.sales.ops.serviceimpl.event.v3.status.enums.RelationErrorCode;
import com.sales.ops.serviceimpl.event.v3.status.service.OrderStatusEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class RcvOrderStatusEventConsumer extends AsyncEventConsumerStrategy {

    //要使用的线程池
    public RcvOrderStatusEventConsumer() {
        super(OpsEventScanner.EventQueue.OrderStatus);
    }

    //消费事件的方法
    @Autowired
    private OrderStatusEventHandler orderStatusEventHandler;
    @Autowired
    private RcvOrderStatusEventAsyncTaskExecutor rcvOrderStatusEventAsyncTaskExecutor;
    @Autowired
    private OpsPropertyConfig opsPropertyConfig;
    @Autowired
    private OpsRedissonLock opsRedissonLock;

    @Override
    protected Future<String> asyncTask(OrderNoInfo orderNo, List<OrderEvent> events) {
        return rcvOrderStatusEventAsyncTaskExecutor.asyncTask(orderNo, events);
    }

    public Future<String> asyncHandle(OrderNoInfo orderNo, List<OrderEvent> events) {
        boolean lock = false;
        String lockKey = "ops:eventLock:" + orderNo.getOrderNo() + "-" + orderNo.getItemNo();
        try {
            lock = opsRedissonLock.addLock(lockKey, 300);
            processEvent(orderNo.getOrderNo(), orderNo.getItemNo(), events.get(0));
            success(orderNo, events);
        } catch (Exception e) {
            failure(orderNo, events, e);
        }finally {
            // 释放锁
            if (lock) {
                opsRedissonLock.releaseLock(lockKey);
            }
        }
        return new AsyncResult<>("Task result");
    }


    @Override
    protected void processEvent(String orderNo, Integer orderItem, OrderEvent event) throws OpsException {
        orderStatusEventHandler.handleOrderStatusUpdated(orderNo, orderItem, event.getId().toString());
    }

    @Override//version累加重试次数
    public Integer getRetryCount() {
        return opsPropertyConfig.getRetryCount();
    }

    @Override
    public List<String> getWhiteError() {
        return Arrays.asList(
                RelationErrorCode.RCV_CANCELED.getDesc(),
                RelationErrorCode.NOT_FOUND_RCV.getDesc()
        );
    }
}
