package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.ExpModelServiceFeignApi;
import com.smc.smccloud.service.PdServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author lyc
 * @Date 2023/6/26 8:14
 * @Descripton TODO
 */
@Component
public class PdServiceFeignApiHystrix implements FallbackFactory<PdServiceFeignApi>  {
    @Override
    public PdServiceFeignApi create(Throwable throwable) {
        return new PdServiceFeignApi() {
            @Override
            public ResultVo<String> execPdImpDataTask() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> execYdPdStepByPlan() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> execYdOpsExtractPdStepByPlan() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> execYdWmsExtractPdStepByPlan() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> preOrderHitRate(String calDate) {
                return ResultVo.failure("服务降级");
            }

        };
    }
}
