package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.borrowstock.BrwApplyDTO;
import com.smc.smccloud.service.BorrowStockFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class BorrowStockFeignApiHystrix implements FallbackFactory<BorrowStockFeignApi> {

    @Override
    public BorrowStockFeignApi create(Throwable throwable) {
        return new BorrowStockFeignApi() {
            @Override
            public ResultVo<String> createBorrowStockApply(BrwApplyDTO brwApplyDTO) {
                return null;
            }
        };
    }

}
