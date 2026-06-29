package com.sales.ops.serviceimpl.event.v3.manage.consumer;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.event.processor.AsyncEventConsumerStrategy;
import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.serviceimpl.event.config.OpsPropertyConfig;
import com.sales.ops.serviceimpl.event.v3.manage.threadPool.RcvOrderDeliveryPlanEventAsyncTaskExecutor;
import com.sales.ops.serviceimpl.event.v3.plan.OrderDeliveryPlanService;
import com.sales.ops.serviceimpl.event.v3.status.enums.RelationErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class RcvOrderDeliveryPlanEventConsumer extends AsyncEventConsumerStrategy {

    public RcvOrderDeliveryPlanEventConsumer() {
        super(OpsEventScanner.EventQueue.DeliveryPlan);
    }

    @Autowired
    private OrderDeliveryPlanService orderDeliveryPlanService;
    @Autowired
    private RcvOrderDeliveryPlanEventAsyncTaskExecutor rcvOrderDeliveryPlanEventAsyncTaskExecutor;
    @Autowired
    private OpsPropertyConfig opsPropertyConfig;
    @Autowired
    private OpsRedissonLock opsRedissonLock;

    //分组规则
    @Override
    public OrderNoInfo groupByOrderNo(OrderEvent orderEvent) {
        return new OrderNoInfo(orderEvent.getOrderId(), 0, null);
    }

    /**
     * 给事件分组并分发线程
     *
     * @param orderEvents
     */
    @Override
    public void handle(List<OrderEvent> orderEvents) {
        //按照七位订单号分组，相同的订单号被合并到一组
        Map<OrderNoInfo, List<OrderEvent>> map7 = orderEvents.stream().collect(Collectors.groupingBy(this::groupByOrderNo));
        //合并小的组，最终保留一共10个组，分十个线程处理
        List<List<OrderEvent>> groups = createGroup(map7.values());
        List<Future<String>> futures = new ArrayList<>();
        groups.forEach(events -> {
            //开启异步任务
            Future<String> future = asyncTask(null, events);
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
    //分组方法A:每次分组都由队列优先级读取组大小
    private List<List<OrderEvent>> createGroupA(Collection<List<OrderEvent>> lists) {
        List<List<OrderEvent>> group = new ArrayList<>(lists);
        PriorityQueue<List<OrderEvent>> queue = new PriorityQueue<>(Comparator.comparing(List::size));
        for (int i = 0; i < 10; i++) {
            queue.add(new ArrayList<>());
        }
        for (List<OrderEvent> eventList : group) {
            List<OrderEvent> poll = queue.poll();
            poll.addAll(eventList);
            queue.offer(poll);
        }
        return new ArrayList<>(queue);
    }

    private List<List<OrderEvent>> createGroup(Collection<List<OrderEvent>> lists) {
        // 创建空的排序队列，定义优先级，
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        for (int i = 0; i < 10; i++) {
            //元素为{组号，组内条数}
            queue.offer(new int[]{i, 0});
        }
        // 对源始数据按从大到小排序
        List<List<OrderEvent>> sortedEventLists = new ArrayList<>(lists);
        sortedEventLists.sort((o1, o2) -> o2.size() - o1.size());
        // 创建结果队列,给结果队列分配十个线程
        List<List<OrderEvent>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(new ArrayList<>());
        }
        for (List<OrderEvent> eventList : sortedEventLists) {
            int[] minGroup = queue.poll();
            int groupIndex = minGroup[0];
            result.get(groupIndex).addAll(eventList);
            queue.offer(new int[]{groupIndex, minGroup[1] + eventList.size()});
        }
        return result;
    }

    // 开启线程池，用异步的方式调用asyncHandle
    @Override
    public Future<String> asyncTask(OrderNoInfo orderNo, List<OrderEvent> events) {
        return rcvOrderDeliveryPlanEventAsyncTaskExecutor.asyncTask(orderNo, events);
    }

    //events里是相同的7位订单
    public Future<String> asyncHandle(OrderNoInfo orderNo, List<OrderEvent> events) {
        //按照10位订单分组，每个10位订单只消费一次，10位订单之间排队处理
        Map<OrderNoInfo, List<OrderEvent>> map10 = events.stream().collect(Collectors.groupingBy(super::groupByOrderNo));

        for (Map.Entry<OrderNoInfo, List<OrderEvent>> entry : map10.entrySet()) {
            OrderNoInfo orderNoInfo = entry.getKey();
            List<OrderEvent> eventList = entry.getValue();
            boolean lock = false;
            String lockKey = "ops:eventLock:" + orderNoInfo.getOrderNo() + "-" + orderNoInfo.getItemNo();
            try {
                lock = opsRedissonLock.addLock(lockKey, 300);
                processEvent(orderNoInfo.getOrderNo(), orderNoInfo.getItemNo(), eventList.get(0));
                success(orderNoInfo, eventList);
            } catch (Exception e) {
                failure(orderNoInfo, eventList, e);
            } finally {
                // 释放锁
                if (lock) {
                    opsRedissonLock.releaseLock(lockKey);
                }
            }
        }
        return new AsyncResult<>("Task result");
    }

    @Override
    protected void processEvent(String orderNo, Integer orderItem, OrderEvent event) throws OpsException {
        orderDeliveryPlanService.handle(orderNo, orderItem, event.getEventCode());
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
