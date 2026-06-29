package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.service.hystrix.SMSOrderServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-04-02 13:22
 * Description:
 */
@FeignClient(name = "adapter-service",
        contextId = "adapter-order",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = SMSOrderServiceFeignHystrix.class)
public interface SMSOrderServiceFeignApi {

    /**
     * 发送订单取消结果到返回结果消息队列
     *
     * @param resultList 订单取消结果
     * @return boolean
     */
    @RequestMapping(value = "/adapter/order/sendOrderCancelReturnMessage", method = RequestMethod.POST)
    ResultVo<Boolean> sendOrderCancelReturnMessage(@RequestBody List<OrderCancelResult> resultList);

}
