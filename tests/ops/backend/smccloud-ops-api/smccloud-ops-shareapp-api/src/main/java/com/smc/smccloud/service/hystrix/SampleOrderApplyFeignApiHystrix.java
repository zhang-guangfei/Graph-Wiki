package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.SmsSendOpsDetailTaskBean;
import com.smc.smccloud.model.sampleorder.*;
import com.smc.smccloud.service.SampleOrderApplyFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SampleOrderApplyFeignApiHystrix implements FallbackFactory<SampleOrderApplyFeignApi> {
    @Override
    public SampleOrderApplyFeignApi create(Throwable throwable) {
        return new SampleOrderApplyFeignApi() {
            @Override
            public ResultVo<List<SampleOrderReturnDTO>> addSampleOrder(SampleOrderDTO sampleOrderDTO) {
                return ResultVo.failure("服务降级");
            }

//            @Override
//            public ResultVo<String> addSampleOrderByList(List<SampleOrderVO> sampleOrderVOList) {
//                return ResultVo.failure("服务降级");
//            }

            @Override
            public ResultVo<String> autoGenerateSampleBalData() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoCreateOrerBySampleOrder() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoOrderCarryTurn() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoInsertSales() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoToSalesInvoice() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> insertSampleBalApply(SampleBalApplyVO info) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> findHandSampleBalApply(FindHandSampleBalHandVO vo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> checkRcvQty(CheckRcvQtyVO checkRcvQtyVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> insertReturnOrder(SmsSendOpsDetailTaskBean bean) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> sureApplySampleBalApi(List<String> ids) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<SampleBalApplyVO> getSampleBalApplyInfo(SampleBalApplyVO info) {
                return ResultVo.failure("服务降级");
            }
        };
    }
}
