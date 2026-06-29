package com.sales.ops.web.controller.event;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDeliveryPlanResult;
import com.sales.ops.db.entity.OpsOrderAssignResult;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.extdao.OrderAssignResultFnoDao;
import com.sales.ops.dto.order.OrderStatusViewVO;
import com.sales.ops.dto.poDeliver.PoDeliverQueryDto;
import com.sales.ops.dto.purchase.PurchaseInvoiceDetailInfo;
import com.sales.ops.dto.purchase.PurchaseOrderDetailInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.event.repository.OpsEventScanner;
import com.sales.ops.event.repository.entity.OrderEvent;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.BaseOrderAssignResultService;
import com.sales.ops.service.po.OrderPurchaseService;
import com.sales.ops.serviceimpl.event.config.OpsPropertyConfig;
import com.sales.ops.serviceimpl.event.v3.deliveryDate.DeliveryDateEventProcessor;
import com.sales.ops.serviceimpl.event.v3.deliveryPush.OrderDeliveryPushEventProcessor;
import com.sales.ops.serviceimpl.event.v3.manage.threadPool.ThreadPoolExecutorMonitor;
import com.sales.ops.serviceimpl.event.v3.order.entity.JYCK;
import com.sales.ops.serviceimpl.event.v3.order.entity.Order;
import com.sales.ops.serviceimpl.event.v3.order.service.OrderRelationService;
import com.sales.ops.serviceimpl.event.v3.plan.OrderDeliveryPlanService;
import com.sales.ops.serviceimpl.event.v3.poDelivery.PoDeliveryEventHandler;
import com.sales.ops.serviceimpl.event.v3.status.service.OrderStatusEventHandler;
import com.sales.ops.serviceimpl.event.v3.status.service.OrderStatusService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/event/v3/test")
public class OpsEventTestController {

    @Autowired
    private OrderPurchaseService orderPurchaseService;
    @Autowired
    private OrderRelationService orderRelationService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private OrderStatusEventHandler orderStatusEventHandler;
    @Autowired
    private OrderDeliveryPlanService orderDeliveryPlanService;
    @Autowired
    private BaseOrderAssignResultService baseOrderAssignResultService;
    @Autowired
    private ThreadPoolExecutorMonitor threadPoolExecutorMonitor;
    @Autowired
    private OpsPropertyConfig opsPropertyConfig;
    @Autowired
    private OrderAssignResultFnoDao orderAssignResultFnoDao;
    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;
    @Autowired
    private OpsEventScanner opsEventScanner;
    @Autowired
    private OrderDeliveryPushEventProcessor orderDeliveryPushEventProcessor;
    @Autowired
    private DeliveryDateEventProcessor deliveryDateEventProcessor;
    @Autowired
    private PoDeliveryEventHandler poDeliveryEventHandler;


    @ApiOperation("计算订单实体")
    @GetMapping("/entity")
    public CommonResult createOrderEntity(@RequestParam String orderId, @RequestParam String orderItem) {
        try {
            Order order = orderRelationService.getOrder(orderId, Integer.valueOf(orderItem));
            return CommonResult.success("数据查询成功", order);
        } catch (OpsException e) {
            log.info("{}", e);
            return CommonResult.failure("数据查询失败");
        }
    }


    @ApiOperation("计算交易出库实体")
    @GetMapping("/jycks/entity")
    public CommonResult createJYCKsEntity(@RequestParam String orderId, @RequestParam String orderItem) {
        try {
            List<JYCK> jycks = orderRelationService.getJYCKs(orderId, Integer.valueOf(orderItem));
            return CommonResult.success("数据查询成功", jycks);
        } catch (OpsException e) {
            log.info("{}", e);
            return CommonResult.failure("数据查询失败");
        }
    }


    @ApiOperation("查询请购单实体")
    @GetMapping("/repo/entity")
    public CommonResult getRequestInfo(@RequestParam String orderNo, @RequestParam Integer itemNo, @RequestParam Integer splitNo) {
        try {
            PurchaseOrderDetailInfo info = orderPurchaseService.getPurchaseInfo(orderNo, itemNo, splitNo);
            return CommonResult.success("数据查询成功", info);
        } catch (Exception e) {
            log.info("{}", e);
            return CommonResult.failure("数据查询失败");
        }
    }


    @ApiOperation("查询采购发票实体")
    @GetMapping("/invoice/entity")
    public CommonResult getRequestInfo(@RequestParam String invoiceNo, @RequestParam Long invoiceId) {
        try {
            PurchaseInvoiceDetailInfo info = orderPurchaseService.getInvoiceInfo(invoiceNo, invoiceId);
            return CommonResult.success("数据查询成功", info);
        } catch (Exception e) {
            log.info("{}", e);
            return CommonResult.failure("数据查询失败");
        }
    }

    @ApiOperation("重新计算订单状态")
    @PutMapping("/status/refresh")
    public CommonResult refreshOrderStatus(@RequestParam String orderId, @RequestParam String orderItem) {
        try {
            orderStatusEventHandler.handleOrderStatusUpdated(orderId, Integer.valueOf(orderItem), null);
            List<OrderStatusViewVO> orderStatusList = orderStatusService.findOrderStatusViewList(orderId, Integer.valueOf(orderItem));
            return CommonResult.success("数据更新成功", orderStatusList);
        } catch (OpsException e) {
            log.info("{}", e);
            return CommonResult.failure(e, "数据更新失败");
        }
    }


    @ApiOperation("重新计算订单交付计划")
    @PutMapping("/plan/refresh")
    public CommonResult refreshOrderPlan(@RequestParam String orderId, @RequestParam String orderItem) {
        try {
            orderDeliveryPlanService.handle(orderId, Integer.valueOf(orderItem), "test");
            List<OpsDeliveryPlanResult> opsDeliveryPlanResults = orderDeliveryPlanService.selectDeliveryPlanResultsByOrderNo(orderId, orderItem);
            return CommonResult.success("数据更新成功", opsDeliveryPlanResults);
        } catch (OpsException e) {
            log.info("{}", e);
            return CommonResult.failure(e, "数据更新失败");
        }
    }

    @ApiOperation("重新计算rcv出库区分")
    @PutMapping("/stockInfo/refresh")
    public CommonResult refreshStockInfo(@RequestParam String orderId, @RequestParam String orderItem) {
        try {
            List<OpsOrderAssignResult> list = baseOrderAssignResultService.findByOrder(orderId, Integer.valueOf(orderItem));
            baseOrderAssignResultService.updateRcvStockInfoByResult(list);
            return CommonResult.success("数据更新成功", list);
        } catch (OpsException e) {
            log.info("{}", e);
            return CommonResult.failure(e, "数据更新失败");
        }
    }


    @ApiOperation("重新计算rcv出库区分")
    @GetMapping("/stockInfo/refresh/batch")
    public CommonResult refreshStockInfo() {
        Map<String, String> result = new HashMap<>();
        List<String> orderFnoList = orderAssignResultFnoDao.selectFnoByOrderAssignResultFno();
        for (String orderFno : orderFnoList) {
            try {
                Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByFno(orderFno);
                List<OpsOrderAssignResult> list = baseOrderAssignResultService.findByOrder(rcvdetail.getRorderNo(), rcvdetail.getRorderItem());
                baseOrderAssignResultService.updateRcvStockInfoByResult(list);
                orderAssignResultFnoDao.updateStatusByOrderFno(orderFno);
            } catch (OpsException e) {
                log.error(e.getMessage());
                result.put(orderFno, e.getMessage());
            }
        }
        return CommonResult.success("数据更新完成", result);
    }

    @ApiOperation("重新计算完纳result")
    @PutMapping("/finish/refresh")
    public CommonResult refreshForceFinish(@RequestParam String orderId, @RequestParam String orderItem) {
        try {

            baseOrderAssignResultService.updateResultForForceFinish(orderId, Integer.valueOf(orderItem));
            return CommonResult.success("数据更新成功");
        } catch (OpsException e) {
            log.info("{}", e);
            return CommonResult.failure(e, "数据更新失败");
        }
    }


    @ApiOperation("重新计算推送交付")
    @PutMapping("/psi/refresh")
    public CommonResult refreshDeliveryInfoPushPsi(@RequestParam Long id) {
        try {
            OrderEvent event = opsEventScanner.scanEventQueueById("ops_event_delivery_push", id);
            orderDeliveryPushEventProcessor.handle(event);
            return CommonResult.success("数据更新成功");
        } catch (OpsException e) {
            log.info("{}", e);
            return CommonResult.failure(e, "数据更新失败");
        }
    }

    @ApiOperation("重新执行交货期事件")
    @PutMapping("/deliverDate/handle")
    public CommonResult refreshDeliveryDate(@RequestParam Long id) {
        try {
            OrderEvent event = opsEventScanner.scanEventQueueById("ops_event_delivery_date", id);
            deliveryDateEventProcessor.handle(event);
            return CommonResult.success("数据更新成功");
        } catch (OpsException e) {
            log.info("{}", e);
            return CommonResult.failure(e, "数据更新失败");
        }
    }

    @ApiOperation("重新执行交货期事件")
    @PutMapping("/poDelivery/handle")
    public CommonResult refreshPoDelivery(@RequestParam String orderFno) {
        try {
            String[] orderFnoArray = orderFno.split("-");
            PoDeliverQueryDto deliverInfo = poDeliveryEventHandler.getDeliverInfoByOrderNo(orderFnoArray[0], Integer.valueOf(orderFnoArray[1]),
                    orderFnoArray.length == 3 ? Integer.valueOf(orderFnoArray[2]) : null);
            return CommonResult.success("数据更新成功",deliverInfo);
        } catch (OpsException e) {
            log.info("{}", e);
            return CommonResult.failure(e, "数据更新失败");
        }
    }

    @GetMapping("/threadPool")
    public CommonResult getThreadPoolInfo(@RequestParam String poolName) {
        Map<String, Object> info = threadPoolExecutorMonitor.getThreadPoolInfo(poolName);
        return CommonResult.success(info);
    }

    @GetMapping("/config")
    public CommonResult getConfig() {
        return CommonResult.success(opsPropertyConfig.toString());
    }

}
