package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.service.hystrix.OpsSalesNoticeTaskFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @Author lyc
 * @Date 2023/7/19 16:45
 * @Descripton TODO
 */
@FeignClient(name = "order-service",
        contextId = "OpsSalesNoticeTask",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = OpsSalesNoticeTaskFeignHystrix.class)
public interface OpsSalesNoticeTaskFeignApi {

    @RequestMapping(value = "/order/execInsertOpsSalesNoticeTask", method = RequestMethod.POST)
    ResultVo<String> execInsertOpsSalesNoticeTask(@RequestBody String paramJson);

    @RequestMapping(value = "/order/opsSalesNoticeTask", method = RequestMethod.GET)
    ResultVo<String> opsSalesNoticeTask();

    @RequestMapping(value = "/order/opsSalesNoticeTask2", method = RequestMethod.GET)
    ResultVo<String> opsSalesNoticeTask2();

    @RequestMapping(value = "/order/opsSalesCallBackNoticeTask", method = RequestMethod.GET)
    ResultVo<String> opsSalesCallBackNoticeTask();

    @RequestMapping(value = "/order/opsSalesCallBackNoticeTask2", method = RequestMethod.GET)
    ResultVo<String> opsSalesCallBackNoticeTask2();

    @PostMapping("/upOpsSalesNoticeTaskInfo")
    ResultVo<String> upOpsSalesNoticeTaskInfo(@RequestBody UpTaskInfoVO vo);

}
