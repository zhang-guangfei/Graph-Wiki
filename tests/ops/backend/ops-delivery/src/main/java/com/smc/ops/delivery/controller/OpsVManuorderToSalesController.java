package com.smc.ops.delivery.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.mapper.OpsVManuorderToSalesMapper;
import com.smc.ops.delivery.model.ips.OpsVManuorderToSales;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/manu")
@Slf4j
public class OpsVManuorderToSalesController {


    @Autowired
    private OpsVManuorderToSalesMapper opsVManuorderToSalesMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/updateVManuorderToSales")
    public CommonResult<OpsVManuorderToSales> updateVManuorderToSales(@RequestBody OpsVManuorderToSales data) {
        try {
            LambdaUpdateWrapper<OpsVManuorderToSales> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(OpsVManuorderToSales::getSalesOrderNo, data.getSalesOrderNo());
            wrapper.set(data.getSalesCancelStatus() != null, OpsVManuorderToSales::getSalesCancelStatus, data.getSalesCancelStatus());
            wrapper.set(data.getSalesStatus() != null, OpsVManuorderToSales::getSalesStatus, data.getSalesStatus());
            wrapper.set(OpsVManuorderToSales::getSalesCancelTime, data.getSalesCancelTime());
            wrapper.set(data.getSalesUpdateTime() != null, OpsVManuorderToSales::getSalesUpdateTime, data.getSalesUpdateTime());
            wrapper.set(OpsVManuorderToSales::getSalesCancelReason, data.getSalesCancelReason());
            wrapper.set(data.getSalesRemark() != null, OpsVManuorderToSales::getSalesRemark, data.getSalesRemark());
            wrapper.set(data.getSalesordernoJp() != null, OpsVManuorderToSales::getSalesordernoJp, data.getSalesordernoJp());
            wrapper.set(data.getExpectedArrivalDate() != null, OpsVManuorderToSales::getExpectedArrivalDate, data.getExpectedArrivalDate());
            wrapper.set(data.getExpectedArrivalinvNo() != null, OpsVManuorderToSales::getExpectedArrivalinvNo, data.getExpectedArrivalinvNo());
            wrapper.set(data.getSalesDeliveryTime() != null, OpsVManuorderToSales::getSalesDeliveryTime, data.getSalesDeliveryTime());
            wrapper.set(data.getExpInvCode() != null, OpsVManuorderToSales::getExpInvCode, data.getExpInvCode());
            wrapper.set(data.getTransType() != null, OpsVManuorderToSales::getTransType, data.getTransType());
            int i = opsVManuorderToSalesMapper.update(null, wrapper);
            if (i == 1) {
                return CommonResult.success(data);
            }
            return CommonResult.failure();
        } catch (Exception e) {
            log.error("更新 OpsVManuorderToSales 失败", e);
            return CommonResult.failure(e.getMessage());
        }
    }
}
