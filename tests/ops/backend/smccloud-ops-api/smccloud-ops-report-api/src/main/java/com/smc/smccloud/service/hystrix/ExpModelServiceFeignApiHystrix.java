package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.ExpModelServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author edp04
 * @title: ExpModelServiceFeignApiHystrix
 * @date 2022/05/11 15:53
 */
@Component
public class ExpModelServiceFeignApiHystrix implements FallbackFactory<ExpModelServiceFeignApi> {

    @Override
    public ExpModelServiceFeignApi create(Throwable throwable) {
        return new ExpModelServiceFeignApi() {

            @Override
            public ResultVo<String> onSendAgentOrderFreqReport(String agentNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> sendSalesEDiscountReport(String date) {
                return ResultVo.failure("服务降级");
            }
        };
    }
}
