package com.sales.ops.api.controller.tms;

import com.sales.ops.api.dto.tms.InquiryFreightCalculationDto;
import com.sales.ops.api.service.tms.OpsPostApiToTmsService;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops调用tms
 * @date ：Created in 2021/12/27 10:45
 *
 * 7.2.	OPS询价运费测算
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/OpsPostApiToTms")
public class OpsPostApiToTmsController {

    @Autowired
    private OpsPostApiToTmsService opsPostApiToTmsService;

    /**
     * 7.1.	承运商信息回传
     * @param param
     * @return
     */
    @RequestMapping("/inquiryFreightCalculation")
    public CommonResult inquiryFreightCalculation(@RequestBody InquiryFreightCalculationDto param) {
        try {
            return opsPostApiToTmsService.inquiryFreightCalculation(param);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }
}
