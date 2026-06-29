package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.stockassembly.StockAssemblyApplyDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyItemDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyRequest;
import com.smc.smccloud.model.stockassembly.TransferResult;
import com.smc.smccloud.service.StockAssemblyFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockAssemblyFeignApiHystrix implements FallbackFactory<StockAssemblyFeignApi> {

    @Override
    public StockAssemblyFeignApi create(Throwable throwable) {
        return new StockAssemblyFeignApi() {

            @Override
            public ResultVo<String> createStockAssemblyApply(StockAssemblyApplyDTO createDto) {
                return ResultVo.failure("服务异常,降级处理");
            }

            @Override
            public ResultVo<List<TransferResult>> handleSMSInStockApply(StockAssemblyApplyDTO createDto) {
                return ResultVo.failure("服务异常,降级处理");
            }

            @Override
            public ResultVo<String> importAssemblyCostData() {
                return ResultVo.failure("服务异常,降级处理");
            }

            @Override
            public ResultVo<String> updateAssemblyStatus(String applyNo, Boolean result) {
                return ResultVo.failure("服务异常,降级处理");
            }
            @Override
            public ResultVo<PageInfo<StockAssemblyItemDTO>> listStockAssemblyApplyDetail(StockAssemblyRequest request){
                return ResultVo.failure("服务异常,降级处理");
            }
            @Override
            public  ResultVo<List<InventoryForAdjustDto>> getAssemblyInDataForWMS(String applyNo ){
                return ResultVo.failure("服务异常,降级处理");
            }
        };
    }
}
