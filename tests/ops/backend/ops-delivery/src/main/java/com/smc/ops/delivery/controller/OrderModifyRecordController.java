package com.smc.ops.delivery.controller;

import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.service.order.OrderModifyRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/4 11:12
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/order")
@Slf4j
public class OrderModifyRecordController {

    @Autowired
    private OrderModifyRecordService orderModifyRecordService;

    /**
     *  bugid: 14562 订单还原、转定数据定时发送
     * @return
     */
    @RequestMapping("/send/orderModifyReport")
    public CommonResult<String> orderModifyReport(){
        try {
            orderModifyRecordService.getOrderModifyReportToSaveExcelAndMailDb();
            return CommonResult.success();
        } catch (Exception e) {
            log.error("订单还原及转定",e);
            return CommonResult.failure();
        }
    }
}
