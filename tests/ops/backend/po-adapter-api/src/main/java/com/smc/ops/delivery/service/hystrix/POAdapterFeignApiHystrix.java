package com.smc.ops.delivery.service.hystrix;

import com.smc.ops.delivery.service.PoAdapterFeignApi;
import com.smc.ops.delivery.util.ResultVo;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author lyc
 * @Date 2024/4/17 8:26
 * @Descripton TODO
 */
@Component
public class POAdapterFeignApiHystrix implements FallbackFactory<PoAdapterFeignApi> {
    @Override
    public PoAdapterFeignApi create(Throwable cause) {
        return new PoAdapterFeignApi() {
            @Override
            public ResultVo<String> CMGetReceivingOrdersAndReturnLetter() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> CMGetInvoiceBarcodePrice() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> GZGetReceivingOrdersAndReturnLetter(String jsonParam) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> GZGetInvoiceBarcodePrice() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> emailParse() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> ftpDeliveryParse() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getManuJPShippedInfoToUnusualData() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getInqueryDataSendingToUnusualData() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getGwInvoiceInfoWithAutoJob() {
                return ResultVo.failure("服务降级");
            }
        };
    }
}
