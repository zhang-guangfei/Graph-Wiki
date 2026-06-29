package com.sales.ops.web.controller.order;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.purchase.PurchaseDeleteDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.wmOrder.OpsChangeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author B91717
 * @date 2022/5/12
 * @apiNote
 */
@RestController
@RequestMapping("/order/purchase")
public class PurchaseDeleteController {

    @Autowired
    private OpsChangeOrderService opsChangeOrderService;


    @PostMapping("/delete")
    public CommonResult purchaseDelete(@RequestBody PurchaseDeleteDto purchaseDeleteDto) {
        try {
            HashMap map = new HashMap();
            boolean result = opsChangeOrderService.askWmServerDelPo( map,purchaseDeleteDto.getOrderno(),purchaseDeleteDto.getItemno(), purchaseDeleteDto.getSplititemno(), purchaseDeleteDto.getRequestpurchaselist());
            return result ? CommonResult.success()
                    : CommonResult.failure("不允许删除采购单,请先转定,需转定客户单号为："+map.get("orderIdAndOrderItem"));
                    //: CommonResult.success();
        } catch (OpsException e) {
            return CommonResult.failure(e.getMessage());
        }
    }
}
