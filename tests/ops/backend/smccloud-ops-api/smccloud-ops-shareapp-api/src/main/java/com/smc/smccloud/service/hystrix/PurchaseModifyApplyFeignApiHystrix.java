package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.ordermodify.PurchaseModifyVO;
import com.smc.smccloud.service.PurchaseModifyApplyFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PurchaseModifyApplyFeignApiHystrix implements FallbackFactory<PurchaseModifyApplyFeignApi> {
    @Override
    public PurchaseModifyApplyFeignApi create(Throwable throwable) {
        return new PurchaseModifyApplyFeignApi() {
            @Override
            public ResultVo<String> handlePurchaseModify() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> insertPurchaseModify(PurchaseModifyVO vo) {
                return ResultVo.failure("服务降级");
            }

        };
    }
}
