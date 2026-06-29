package com.sales.ops.feign.inventory;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ：C14717
 * @version: $ 19127
 * @description：
 * @date ：Created in 2025/11/4 10:59
 */
@FeignClient(name = "wm-service", contextId = "InvRiskFeign")
public interface InvRiskFeign {

    @RequestMapping(value = "/invRisk/data", method = RequestMethod.GET)
    ResultVo<String> handleExe();
}
