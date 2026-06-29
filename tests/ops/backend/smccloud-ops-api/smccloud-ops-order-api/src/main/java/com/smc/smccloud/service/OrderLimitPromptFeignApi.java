package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.hystrix.OrderLimitPromptFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author ：C14717
 * @version: $
 * @description： bugid:18113
 * @date ：Created in 2025/7/14 13:53
 */
@FeignClient(name = "order-service",
        contextId = "orderlimitprompt",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = OrderLimitPromptFeignHystrix.class)
public interface OrderLimitPromptFeignApi {

    @RequestMapping(value = "/order/limitPrompt/handle", method = RequestMethod.GET)
    ResultVo<String> handle();
}
