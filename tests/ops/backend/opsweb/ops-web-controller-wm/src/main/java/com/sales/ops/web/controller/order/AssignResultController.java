package com.sales.ops.web.controller.order;

import com.sales.ops.db.entity.OpsOrderAssignResult;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.order.BaseOrderAssignResultService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author C12961
 * @date 2022/4/16 11:02
 */
@CrossOrigin
@RestController
@RequestMapping("/order/rcvorder/assign")
public class AssignResultController {


    @Resource
    private BaseOrderAssignResultService baseOrderAssignResultService;


    @GetMapping("/search/order")
    public CommonResult searchByOrder(@RequestParam String orderId, @RequestParam Integer orderItem) {
        List<OpsOrderAssignResult> result = baseOrderAssignResultService.findByOrder(orderId, orderItem);
        if (result.isEmpty()) {
            result = baseOrderAssignResultService.findDeletedByOrder(orderId, orderItem);
        }
        return CommonResult.success(result);
    }


    @PostMapping("/search/po")
    public CommonResult searchByPo(@RequestBody OpsPurchaseorder order) {
        List<OpsOrderAssignResult> result = baseOrderAssignResultService.findByPo(order.getOrderno(), order.getItemno(), order.getSplititemno());
        return CommonResult.success(result);
    }

}
