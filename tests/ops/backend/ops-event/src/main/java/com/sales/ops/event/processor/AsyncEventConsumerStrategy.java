package com.sales.ops.event.processor;

import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@Component
public abstract class AsyncEventConsumerStrategy implements EventConsumerStrategy {

    @Autowired
    private OpsEventScanner opsEventScanner;

    protected OpsEventScanner.EventQueue eventQueue;
    private Integer retryCount = 10;


    public AsyncEventConsumerStrategy(OpsEventScanner.EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public List<String> getWhiteError() {
        return new ArrayList<>();
    }

    //执行器流程
    public void handle(List<OrderEvent> orderEvents) {
        //按照十位订单号分组，相同的订单号被合并到一组
        Map<OrderNoInfo, List<OrderEvent>> map = orderEvents.stream().collect(Collectors.groupingBy(this::groupByOrderNo));
        //每个订单号只执行一次
        List<Future<String>> futures = new ArrayList<>();
        map.forEach((orderNo, events) -> {
            //开启异步任务
            Future<String> future = asyncTask(orderNo, events);
            futures.add(future);
        });
        // 等待所有异步任务完成并收集结果
        List<String> results = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                results.add(future.get()); // 会阻塞直到异步任务完成
            } catch (InterruptedException | ExecutionException e) {
                // 处理异常
                e.printStackTrace();
            }
        }
    }


    protected abstract Future<String> asyncTask(OrderNoInfo orderNo, List<OrderEvent> events);


    protected abstract void processEvent(String orderNo, Integer orderItem, OrderEvent event) throws Exception;

    protected void success(OrderNoInfo orderNo, List<OrderEvent> events) {
        for (OrderEvent event : events) {
            opsEventScanner.updateEventQueueToHandled(eventQueue.getName(), event.getId(), event.getVersion());
        }
    }

    protected void failure(OrderNoInfo orderNo, List<OrderEvent> events, Exception e) {
        log.error("", e);
        String remark = StringUtils.substring(e.getMessage(), 0, 1000);
        //提取空指针异常的栈信息
        if (e instanceof NullPointerException) {
            String stackTrace = ThrowableUtil.getStackTrace(e);
            remark = StringUtils.substring(stackTrace, 0, 1000);
        }
        //异常白名单，不重试
        if (!CollectionUtils.isEmpty(getWhiteError()) && getWhiteError().contains(e.getMessage())) {
            for (OrderEvent event : events) {
                opsEventScanner.updateEventQueueToHandled(eventQueue.getName(), event.getId(), event.getVersion());
            }
            return;
        }

        //重试第一条
        if (!CollectionUtils.isEmpty(events) && events.size() == 1) {
            OrderEvent event = events.get(0);
            if (event.getVersion() < getRetryCount()) {
                opsEventScanner.updateEventQueueToInit(eventQueue.getName(), event.getId(), remark);
            } else {
                opsEventScanner.updateEventQueueToFailure(eventQueue.getName(), event.getId(), remark);
            }
        } else {
            //获取最大版本号的事件
            events.sort(Comparator.comparing(OrderEvent::getVersion).reversed());
            for (int i = 0; i < events.size(); i++) {
                OrderEvent event = events.get(i);
                //如果是第一条，则
                if (i == 0) {
                    if (event.getVersion() < getRetryCount()) {
                        opsEventScanner.updateEventQueueToInit(eventQueue.getName(), event.getId(), remark);
                    } else {
                        opsEventScanner.updateEventQueueToFailure(eventQueue.getName(), event.getId(), remark);
                    }
                }
                //如果失败的不是第一条，则更新为成功
                else {
                    opsEventScanner.updateEventQueueToHandled(eventQueue.getName(), event.getId(), event.getVersion());
                }
            }
        }
    }


    public OrderNoInfo groupByOrderNo(OrderEvent orderEvent) {
        return new OrderNoInfo(orderEvent.getOrderId(), orderEvent.getOrderItem(), null);
    }


}
