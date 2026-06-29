package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adjust.StockAdjustApplyDTO;
import com.smc.smccloud.model.adjust.StockAdjustDTO;
import com.smc.smccloud.model.adjust.StockAdjustRequest;
import com.smc.smccloud.model.adjust.StockAdjustVO;
import com.smc.smccloud.service.StockAdjustServiceFeignApi;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Component
public class StockAdjustServiceFeignHystrix implements FallbackFactory<StockAdjustServiceFeignApi> {
    @Override
    public StockAdjustServiceFeignApi create(Throwable cause) {
        return new StockAdjustServiceFeignApi() {
            @Override
            public ResultVo<String> addAdjustData(StockAdjustApplyDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<StockAdjustVO>> listAdjustData(StockAdjustRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public void exportStockAdjustData(StockAdjustRequest request) {
                return;
            }

            @Override
            public ResultVo<String> delAdjustData(Integer id) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> createInvoiceNo() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<StockAdjustDTO> getOrderInfoForImpAdjuest(String fullOrderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> importStockAdjustData(MultipartFile file) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> determineStockAdjust(String invoiceNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> threeCProductAdjust() {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<String> createStockAssembleFromInvoiceError() {
                return ResultVo.failure("服务降级");
            }

        };
    }
}
