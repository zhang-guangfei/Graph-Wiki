package com.sales.ops.web.controller.event;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.event.bus.service.EventBusService;
import com.sales.ops.event.exchange.service.EventExchangeService;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.event.v3.manage.consumer.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/event/v3")
public class OpsEventController {



    @Autowired
    private OpsEventScanner opsEventScanner;
    @Autowired
    private EventExchangeService eventExchangeService;
    @Autowired
    private EventBusService eventBusService;
    @Autowired
    private RcvOrderStatusEventConsumer rcvOrderStatusEventConsumer;
    @Autowired
    private RcvOrderDeliveryPlanEventConsumer rcvOrderDeliveryPlanEventConsumer;
    @Autowired
    private RcvOrderStockAssignEventConsumer rcvOrderStockAssignEventConsumer;
    @Autowired
    private OpsPurchaseOrderEventConsumer opsPurchaseOrderEventConsumer;
    @Autowired
    private OpsPoDeliveryEventConsumer opsPoDeliveryEventConsumer;
    @Autowired
    private OpsStockAdjustEventConsumer opsStockAdjustEventConsumer;
    @Autowired
    private RcvOrderDeliveryPushConsumer rcvOrderDeliveryPushConsumer;

    @Autowired
    private CustomerEventPublisher customerEventPublisher;
    @Autowired
    private OpsOrderDeliveryDateConsumer opsOrderDeliveryDateConsumer;


    /**
     * bugid：17305 doconfirm 补偿传事件
     * @param orderId
     * @param orderItem
     */
    @GetMapping("/publisherWMFinished")
    public void warehouseDeliveryFinished(@RequestParam String orderId, @RequestParam Integer orderItem){
        customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, orderId,orderItem);

    }

    @ApiOperation("总线预处理器")
    @GetMapping("/preprocess")
    public CommonResult preprocess(@RequestParam Integer size) {
        // 1.拿到一批事件
        List<OrderEvent> events = opsEventScanner.scanEventBusBySize(size);
        // 2.对每条事件单独处理
        eventBusService.preprocess(events);
        return CommonResult.success();
    }

    @ApiOperation("事件交换机")
    @GetMapping("/exchange")
    public CommonResult exchange(@RequestParam Integer size) {
        // 1.拿到一批事件
        List<OrderEvent> events = opsEventScanner.scanEventPoolBySize(size);
        // 2.对每条事件单独处理
        eventExchangeService.handle(events);
        return CommonResult.success();
    }

    @ApiOperation("订单状态事件队列")
    @GetMapping("/queue/orderStatus")
    public CommonResult orderStatusQueue(@RequestParam Integer size, @RequestParam(required = false,defaultValue = "1") Integer groupId) {
        List<OrderEvent> orderEvents = opsEventScanner.scanEventQueueBySizeAndGroup(OpsEventScanner.EventQueue.OrderStatus.getName(), size, groupId);
        rcvOrderStatusEventConsumer.handle(orderEvents);
        return CommonResult.success();
    }

    @ApiOperation("订单交付计划事件队列")
    @GetMapping("/queue/deliveryPlan")
    public CommonResult orderDeliveryPlanQueue(@RequestParam Integer size) {
        List<OrderEvent> orderEvents = opsEventScanner.scanEventQueueBySize(OpsEventScanner.EventQueue.DeliveryPlan.getName(), size);
        rcvOrderDeliveryPlanEventConsumer.handle(orderEvents);
        return CommonResult.success();
    }

    @ApiOperation("库存分配事件队列")
    @GetMapping("/queue/stockAssign")
    public CommonResult orderStockAssignQueue(@RequestParam Integer size) {
        List<OrderEvent> orderEvents = opsEventScanner.scanEventQueueBySize(OpsEventScanner.EventQueue.StockAssign.getName(), size);
        rcvOrderStockAssignEventConsumer.handle(orderEvents);
        return CommonResult.success();
    }

    @ApiOperation("库存调整事件队列")
    @GetMapping("/queue/stockAdjust")
    public CommonResult stockAdjustQueue(@RequestParam Integer size) {
        List<OrderEvent> orderEvents = opsEventScanner.scanEventQueueBySize(OpsEventScanner.EventQueue.StockAdjust.getName(), size);
        opsStockAdjustEventConsumer.handle(orderEvents);
        return CommonResult.success();
    }

    @ApiOperation("采购单事件队列")
    @GetMapping("/queue/purchaseOrder")
    public CommonResult purchaseOrderQueue(@RequestParam Integer size) {
        List<OrderEvent> orderEvents = opsEventScanner.scanEventQueueBySize(OpsEventScanner.EventQueue.PurchaseOrder.getName(), size);
        opsPurchaseOrderEventConsumer.handle(orderEvents);
        return CommonResult.success();
    }

    @ApiOperation("采购交付事件队列")
    @GetMapping("/queue/poDeliveryQueue")
    public CommonResult poDeliveryQueue(@RequestParam Integer size) {
        List<OrderEvent> orderEvents = opsEventScanner.scanEventQueueBySize(OpsEventScanner.EventQueue.PoDelivery.getName(), size);
        opsPoDeliveryEventConsumer.handle(orderEvents);
        return CommonResult.success();
    }

    @ApiOperation("采购单事件队列")
    @GetMapping("/queue/deliveryPush")
    public CommonResult deliveryPushQueue(@RequestParam Integer size) {
        List<OrderEvent> orderEvents = opsEventScanner.scanEventQueueBySize(OpsEventScanner.EventQueue.DeliveryPush.getName(), size);
        rcvOrderDeliveryPushConsumer.handle(orderEvents);
        return CommonResult.success();
    }

    @ApiOperation("采购单事件队列")
    @GetMapping("/queue/deliveryDate")
    public CommonResult deliveryDateQueue(@RequestParam Integer size) {
        List<OrderEvent> orderEvents = opsEventScanner.scanEventQueueBySize(OpsEventScanner.EventQueue.DeliveryDate.getName(), size);
        opsOrderDeliveryDateConsumer.handle(orderEvents);
        return CommonResult.success();
    }




}
