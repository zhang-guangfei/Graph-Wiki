package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.service.SMSOrderServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-04-02 13:23
 * Description:
 */
@Component
public class SMSOrderServiceFeignHystrix implements FallbackFactory<SMSOrderServiceFeignApi> {

    @Override
    public SMSOrderServiceFeignApi create(Throwable cause) {
        return new SMSOrderServiceFeignApi() {

            @Override
            public ResultVo<Boolean> sendOrderCancelReturnMessage(List<OrderCancelResult> resultList) {
                return ResultVo.failure("接口异常,服务降级.");
            }
        };
    }
}
