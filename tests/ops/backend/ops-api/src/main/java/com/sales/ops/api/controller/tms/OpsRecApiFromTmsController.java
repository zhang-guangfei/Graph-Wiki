package com.sales.ops.api.controller.tms;

import com.sales.ops.api.dto.tms.BillingDataDto;
import com.sales.ops.api.dto.tms.CarrierInfoDto;
import com.sales.ops.api.dto.tms.LogisticsRoutingInfoDto;
import com.sales.ops.api.service.tms.OpsRecApiFromTmsService;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：wms 调用 ops
 * @date ：Created in 2021/12/27 11:25
 * 7.1.	承运商信息回传
 * 7.3.	TMS推送物流路由信息
 * 7.4.	TMS推送账单数据
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/OpsRecApiFromTms")
public class OpsRecApiFromTmsController {

    @Autowired
    private OpsRecApiFromTmsService opsRecApiFromTmsService;

    /**
     * 7.1.	承运商信息回传
     * @param param
     * @return
     */
    @RequestMapping("/carrierInfo")
    public CommonResult carrierInfo(@RequestBody CarrierInfoDto param) {
        try {
            return opsRecApiFromTmsService.carrierInfo(param);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * 7.3.	TMS推送物流路由信息
     * @param param
     * @return
     */
    @RequestMapping("/logisticsRoutingInfo")
    public CommonResult logisticsRoutingInfo(@RequestBody LogisticsRoutingInfoDto param) {
        try {
            return opsRecApiFromTmsService.logisticsRoutingInfo(param);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }

    /**
     * 7.4.	TMS推送账单数据
     * @param param
     * @return
     */
    @RequestMapping("/billingData")
    public CommonResult billingData(@RequestBody BillingDataDto param) {
        try {
            return opsRecApiFromTmsService.billingData(param);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }



}
