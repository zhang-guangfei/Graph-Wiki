package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "report-service",
        contextId = "poReply",
        configuration = AuthFeignAutoConfiguration.class)
public interface PoReplyPushJobFeignApi {

    @GetMapping("/report/poReply/exportExcel")
     ResultVo poReplyExportExcel();
}