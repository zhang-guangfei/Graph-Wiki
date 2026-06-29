package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.service.OpsSalesNoticeTaskFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author lyc
 * @Date 2023/7/19 17:17
 * @Descripton TODO
 */
@Component
public class OpsSalesNoticeTaskFeignHystrix implements FallbackFactory<OpsSalesNoticeTaskFeignApi> {
    @Override
    public OpsSalesNoticeTaskFeignApi create(Throwable throwable) {
        return new OpsSalesNoticeTaskFeignApi() {

            @Override
            public ResultVo<String> execInsertOpsSalesNoticeTask(String paramJson) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> opsSalesNoticeTask() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> opsSalesNoticeTask2() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> opsSalesCallBackNoticeTask() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> opsSalesCallBackNoticeTask2() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> upOpsSalesNoticeTaskInfo(UpTaskInfoVO vo) {
                return ResultVo.failure("服务降级");
            }
        };
    }
}
