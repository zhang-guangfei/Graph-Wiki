package com.sales.ops.event.repository;


import com.sales.ops.event.repository.entity.OrderEvent;

import java.util.List;

/**
 * @author C12961
 * @date 2023/3/29 14:42
 */

public interface OpsEventScanner {


    enum EventQueue {
        OldOrderStatus("order_event", "旧订单状态队列表"),
        OrderStatus("ops_event_order_status", "订单状态队列表"),
        StockAssign("ops_event_stock_assign", "库存分配队列表"),
        DeliveryPlan("ops_event_delivery_plan", "交付计划队列表"),
        StockAdjust("ops_event_stock_adjust", "调库调整队列表"),
        PurchaseOrder("ops_event_purchase_order", "采购单队列表"),
        DeliveryPush("ops_event_delivery_push", "交付计划推送队列表"),
        DeliveryDate("ops_event_delivery_date", "交货期队列表"),
        PoDelivery("ops_event_po_delivery", "采购交付状态更新队列表"),
        ;


        EventQueue(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        private String name;
        private String desc;


        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }
    }

    int insertEventBus(OrderEvent bus);

    List<OrderEvent> scanEventBusBySize(Integer size);

    int insertEventPool(List<OrderEvent> eventList);

    OrderEvent scanEventBusByBusId(Long busId);

    int updateEventBusToHandled(Long id, Long version);

    int updateEventBusToHandling(Long id, Long version);

    int updateEventBusToInit(Long id, String remark);

    int updateEventBusToFailure(Long id, String remark);

    int insertEventPool(OrderEvent pool);

    List<OrderEvent> scanEventPoolBySize(Integer size);

    int updateEventPoolToHandled(Long id, Long version);

    int updateEventPoolToFailure(Long id, String remark);

    int insertEventQueueBatch(List<OrderEvent> eventList, String queue);

    List<String> selectQueueByEventCode(String eventCode);

    int updateEventQueueToInit(String queueName, Long id, String remark);

    int insertEventQueue(OrderEvent event, String queue);


    OrderEvent  scanEventQueueById(String queueName, Long id);

    List<OrderEvent> scanEventQueueBySize(String queueName, Integer size);

    //查询队列表按照事件组别
    List<OrderEvent> scanEventQueueBySizeAndGroup(String queueName, Integer size, Integer groupId);

    int updateEventQueueToHandled(String queueName, Long id, Long version);

    int updateEventQueueToFailure(String queueName, Long id, String remark);
}
