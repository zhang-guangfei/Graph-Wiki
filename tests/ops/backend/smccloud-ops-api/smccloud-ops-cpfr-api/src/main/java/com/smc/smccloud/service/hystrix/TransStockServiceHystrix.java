package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.trans.TransOrderCancelDTO;
import com.smc.smccloud.model.trans.TransOrderRequest;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.service.TransStockFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransStockServiceHystrix implements FallbackFactory<TransStockFeignApi> {
    @Override
    public TransStockFeignApi create(Throwable throwable) {
        return new TransStockFeignApi() {

            @Override
            public ResultVo<String> transStock(List<TransOrderVO> voList) {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<String> transStockAll(List<TransOrderVO> voList) {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<String> createTransOrderForMove(List<TransOrderDtoForMove> voList) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<TransOrderVO>> findTransOrder(TransOrderRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> cancelTransOrder(TransOrderCancelDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public void exportTransData(TransOrderRequest request) {
                return;
            }
        };
    }
}
