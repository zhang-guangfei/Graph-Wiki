package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "report-service",
        contextId = "delay",
        configuration = AuthFeignAutoConfiguration.class)
public interface DelayOrderFeignApi {


    @GetMapping("/report/delayOrder")
    ResultVo calDelayOrderList();
}
