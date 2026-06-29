package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.hystrix.ExpModelServiceFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author edp04
 * @title: ExpModelServiceFeignApi
 * @date 2022/05/11 13:29
 */
@FeignClient(name = "report-service",
        contextId = "exporder",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = ExpModelServiceFeignApiHystrix.class)
public interface ExpModelServiceFeignApi {

    @RequestMapping(value = "/report/order/onSendAgentOrderFreqReport", method = RequestMethod.GET)
    ResultVo<String> onSendAgentOrderFreqReport(@RequestParam("agentNo") String agentNo);

    /**
     * 发送E差益统计邮件
     * 财务、运营、价格课
     * 参数: yy-MM
     * @return
     */
    @RequestMapping(value = "/report/order/sendSalesEDiscountReport", method = RequestMethod.GET)
    ResultVo<String> sendSalesEDiscountReport(@RequestParam("date") String date);

}
