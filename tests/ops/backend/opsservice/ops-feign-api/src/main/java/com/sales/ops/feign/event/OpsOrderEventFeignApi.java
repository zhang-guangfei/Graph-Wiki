package com.sales.ops.feign.event;

import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author C12961
 * @date 2022/2/23 9:24
 */
@FeignClient(name = "wm-service", contextId = "OrderEvent")
public interface OpsOrderEventFeignApi {

    @GetMapping("/order/rcvorder/credit")
    CommonResult checkCredit();
    //bugid：17305 doconfirm 补偿传事件
    @GetMapping("/event/v3/publisherWMFinished")
    CommonResult warehouseDeliveryFinished(@RequestParam String orderId, @RequestParam Integer orderItem);

    @GetMapping("/event/v3/preprocess")
    CommonResult preprocess(@RequestParam Integer size);

    @GetMapping("/event/v3/exchange")
    CommonResult exchange(@RequestParam Integer size);

    @GetMapping("/event/v3/queue/orderStatus")
    CommonResult orderStatusQueue(@RequestParam Integer size, @RequestParam(required = false,defaultValue = "1")  Integer groupId);


    @GetMapping("/event/v3/queue/stockAssign")
    CommonResult orderAssignQueue(@RequestParam Integer size);

    @GetMapping("/event/v3/queue/deliveryPlan")
    CommonResult orderDeliveryPlanQueue(@RequestParam Integer size);

    @GetMapping("/event/v3/queue/stockAdjust")
    CommonResult stockAdjustQueue(@RequestParam Integer size);

    @GetMapping("/event/v3/queue/purchaseOrder")
    CommonResult purchaseOrderQueue(@RequestParam Integer size);

    @GetMapping("/event/v3/queue/poDeliveryQueue")
    CommonResult poDeliveryQueue(@RequestParam Integer size);

    @GetMapping("/event/v3/queue/deliveryPush")
    CommonResult orderDeliveryPushQueue(@RequestParam Integer size);

    @GetMapping("/event/v3/queue/deliveryDate")
    CommonResult orderDeliveryDateQueue(@RequestParam Integer size);
}
