package com.sales.ops.web.controller;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.purchase.RequestPurchaseRestoreService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author B91717
 * @date 2023-11-01
 * 采购拦截自动还原
 */
@CrossOrigin
@RestController
@RequestMapping("/requestPurchaseRestore")
public class RequestPurchaseRestoreController {

    @Resource
    private RequestPurchaseRestoreService requestPurchaseRestoreService;


    /**
     * 自动进行特定拦截订单，进行还原 重新计算操作
     * @return
     */
    @RequestMapping(value = "/autoRestore")
    public CommonResult autoRestore() {
        try {
            String a = requestPurchaseRestoreService.interceptRestore();
            return CommonResult.success(a);
        } catch (Exception e) {
            String message = "样品订单抽取数据到sample_bal出错：" + e.getMessage();
            return CommonResult.failure(message);
        }

    }

}
