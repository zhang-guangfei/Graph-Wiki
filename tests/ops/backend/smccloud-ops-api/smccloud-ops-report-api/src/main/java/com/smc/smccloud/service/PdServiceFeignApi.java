package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.hystrix.ExpModelServiceFeignApiHystrix;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lyc
 * @Date 2023/6/26 8:11
 * @Descripton TODO
 */
@FeignClient(name = "report-service",
        contextId = "pd",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = ExpModelServiceFeignApiHystrix.class)
public interface PdServiceFeignApi {

    @RequestMapping(value = "/report/pd/execPdImpDataTask", method = RequestMethod.GET)
    ResultVo<String> execPdImpDataTask();

    // 根据执行计划执行月度盘点
    @RequestMapping(value = "/report/pd/execYdPdStepByPlan", method = RequestMethod.GET)
    ResultVo<String> execYdPdStepByPlan();

    @RequestMapping(value = "/report/pd/execYdOpsExtractPdStepByPlan", method = RequestMethod.GET)
    ResultVo<String> execYdOpsExtractPdStepByPlan();

    @RequestMapping(value = "/report/pd/execYdWmsExtractPdStepByPlan", method = RequestMethod.GET)
    ResultVo<String> execYdWmsExtractPdStepByPlan();


    // 先行在库命中率基础数据计算
    @RequestMapping(value = "/report/pd/preOrderHitRate", method = RequestMethod.GET)
    ResultVo<String> preOrderHitRate(@RequestParam("calDate") String calDate);
}
