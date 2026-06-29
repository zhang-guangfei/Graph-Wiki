package com.sales.ops.web.controller.inventory.adjustment;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.inventory.adjustment.AdjustInventoryQtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author C12961
 * @date 2022/11/7 16:42
 */
@RestController
@RequestMapping("/adjust/inventory/qty")
public class AdjustInventoryQtyController {

    @Autowired
    private AdjustInventoryQtyService adjustInventoryQtyService;


    @GetMapping("/diff/create")
    public CommonResult createOpsInventoryDiff() {
        try {
            adjustInventoryQtyService.createOpsInventoryDiff();
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failure(e.getMessage());
        }
    }

    @GetMapping("/adjust/create")
    public CommonResult createOpsInventoryAdj(@RequestParam(required = false, name = "num", defaultValue = "1") Integer num) {
        try {
            adjustInventoryQtyService.handleOpsInventoryDiff(num);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failure(e.getMessage());
        }
    }


    @GetMapping("/adjust/update")
    public CommonResult handleOpsInventoryAdj(@RequestParam(required = false, name = "num", defaultValue = "1") Integer num) {
        try {
            adjustInventoryQtyService.handleOpsInventoryAdj(num);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failure(e.getMessage());
        }
    }


}
