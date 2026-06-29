package com.sales.ops.web.controller.order;

import com.sales.ops.db.entity.OrderStatus;
import com.sales.ops.dto.order.OrderStatusViewVO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.serviceimpl.event.v3.status.service.OrderStatusService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/order")
public class CustomerOrderController {

    @Autowired
    private OrderStatusService orderStatusService;


    @ApiOperation("查询订单状态表")
    @GetMapping("/status/search")
    public CommonResult getOrderStatus(@RequestParam String orderId, @RequestParam String orderItem) {
        List<OrderStatusViewVO> orderStatusList = orderStatusService.findOrderStatusViewList(orderId, Integer.valueOf(orderItem));
        return CommonResult.success(orderStatusList);
    }

    /**
     * 根据完整订单号查询orderId,order_item,split_no,split_type
     * 订单修改界面转订查询页面用于转换单号orderFno => ZDFindItemInvDto (/showItem)
     * bugid:18384 c14717 20250728
     *
     * @param orderFno
     */
    @ApiOperation("根据完整单号查询订单信息")
    @GetMapping("/status/getOrderStatusInfoByOrderFno")
    public CommonResult getOrderStatusInfo(@RequestParam String orderFno) {
        try {
            OrderStatus orderStatus = orderStatusService.getOrderStatusInfoByOrderFno(orderFno);
            if(Objects.isNull(orderStatus)){
                return CommonResult.failure("无订单数据");
            }
            return CommonResult.success(orderStatus);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }
    
    
    

}
