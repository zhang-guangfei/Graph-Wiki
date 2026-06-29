package com.smc.smccloud.web;

import cn.hutool.json.JSONArray;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.service.PurchaseGzOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author B91717
 * @date 2022/5/9
 * @apiNote
 */
@RestController
@RequestMapping(value = "/shareapp/purchasegzorder")
public class PurchaseGzOrderController {
    @Resource
    private PurchaseGzOrderService purchaseGzOrderService;

    /**
     * 写入广州制造表
     */
    @PostMapping("/insertGZ")
    public CommonResult<String> insertGZ(@RequestBody JSONArray jsonArray) {
        try {
            return purchaseGzOrderService.insertGZ(jsonArray);
        } catch (Exception e) {
            return CommonResult.failure(e.toString());
        }
    }

}
