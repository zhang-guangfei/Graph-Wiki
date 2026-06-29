package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.service.BinorderServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @author wuweidong
 * @create 2023/12/8 13:46
 * @description
 */
@Component
public class BinOrderServiceFeignHystrix implements FallbackFactory<BinorderServiceFeignApi> {
    @Override
    public BinorderServiceFeignApi create(Throwable throwable) {
        return new BinorderServiceFeignApi() {
            @Override
            public ResultVo<PageInfo<ModelExpFreqVO>> listModelExpFreq(ModelExpFreqRequest dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<ModelExpDetailVO>> listModeldetail(ModelExpFreqRequest dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<ModelExpDetailVO>> listModeldetailByJob(@RequestBody ModelExpFreqRequest dto) {
                return ResultVo.failure("服务降级");
            }


            @Override
            public ResultVo<List<BinOrderInventoryInfoVO>> listBinWarehouseStock(String modelNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> calcmodelExpFreq(int type) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public Date getLastPurchaseDlvDate(@RequestParam("modelNo") String modelNo, @RequestParam("warehouseCode") String warehouseCode) {
                return null;
            }

            @Override
            public ResultVo<String> runBinTrialJob() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<ModelExpFreqVO>> getModelExpFreqForAvgQty(List<String> modelNoS, String warehouseCode, Integer month) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<BinOrderCalcVO> newBinOrderCalcId(@RequestBody BinOrderCalcRequestVO vo) {
                return ResultVo.failure("服务降级");
            }
            @Override
            public ResultVo<BinOrderCalcVO> calcBinOrder(@RequestBody BinOrderCalcRequestVO vo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> finishbinordercalc(Long id) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<BinOrderCalcVO>> findBinOrderCalcByWarehouseCode(String warehouseCode) {
                return ResultVo.failure("服务降级");
            }
        };
    }

}
