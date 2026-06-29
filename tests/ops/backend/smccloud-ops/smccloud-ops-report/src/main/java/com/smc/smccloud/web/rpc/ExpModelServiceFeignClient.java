package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.ExpModelService;
import com.smc.smccloud.service.ExpModelServiceFeignApi;
import com.smc.smccloud.service.SalesDataService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author edp04
 * @title: ExpModelServiceFeignClient
 * @date 2022/05/11 16:00
 */
@RestController
public class ExpModelServiceFeignClient implements ExpModelServiceFeignApi {

    @Resource
    private ExpModelService expModelService;
    @Resource
    private SalesDataService salesDataService;

    @Override
    public ResultVo<String> onSendAgentOrderFreqReport(String agentNo) {
        return expModelService.onSendAgentOrderFreqReport(agentNo);
    }

    @Override
    public ResultVo<String> sendSalesEDiscountReport(String date) {
        return salesDataService.sendSalesEDiscountReport(date);
    }
}
