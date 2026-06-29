package com.smc.smccloud.web;

import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.service.PurchaseIpsOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author B91717
 * @date 2022/5/9
 * @apiNote
 */
@RestController
@RequestMapping(value = "/shareapp/ipsPurchaseSend")
public class PurchaseIpsOrderController {
    @Resource
    private PurchaseIpsOrderService purchaseIpsOrderService;

    /**
     * 写入IPS接单表中
     */
    @PostMapping("/insertIpsOrder")
    public CommonResult<String> insertIpsPurchaseOrder(@RequestBody List<IpsReceiveOrderAllOriginalInfoDto> ipsReceiveOrderAllOriginalInfoDtos) {
        try {
            return purchaseIpsOrderService.insertIpsOrder(ipsReceiveOrderAllOriginalInfoDtos);
        } catch (Exception e) {
            return CommonResult.failure(e.toString());
        }
    }

}
