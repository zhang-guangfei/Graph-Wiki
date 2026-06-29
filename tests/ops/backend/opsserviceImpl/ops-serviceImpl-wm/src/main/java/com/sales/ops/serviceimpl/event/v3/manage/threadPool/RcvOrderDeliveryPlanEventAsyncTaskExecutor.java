package com.sales.ops.serviceimpl.event.v3.manage.threadPool;

import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.serviceimpl.event.v3.manage.consumer.RcvOrderDeliveryPlanEventConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class RcvOrderDeliveryPlanEventAsyncTaskExecutor {
    @Lazy
    @Autowired
    private RcvOrderDeliveryPlanEventConsumer rcvOrderDeliveryPlanEventConsumer;

    @Bean(name = "EventDeliveryPlanTaskExecutor")
    public ThreadPoolTaskExecutor eventDeliveryPlanTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 核心线程数
        executor.setMaxPoolSize(20); // 最大线程数
        executor.setQueueCapacity(500); // 队列容量
        executor.setThreadNamePrefix("Event-Delivery-Plan-"); // 线程名前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//拒绝策略执行器：由调用线程执行任务，降低提交速度。
        executor.initialize();
        return executor;
    }

    @Async("EventDeliveryPlanTaskExecutor")
    public Future<String> asyncTask(OrderNoInfo orderNo, List<OrderEvent> events) {
        return rcvOrderDeliveryPlanEventConsumer.asyncHandle(orderNo, events);
    }

}
