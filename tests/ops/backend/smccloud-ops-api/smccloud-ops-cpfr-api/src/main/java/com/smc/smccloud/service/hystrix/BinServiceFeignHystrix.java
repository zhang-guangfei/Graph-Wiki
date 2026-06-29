package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.bindata.BinDataQueryRequest;
import com.smc.smccloud.model.bindata.BindataRequest;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.model.binorder.CustomerExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.service.BinServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@Component
public class BinServiceFeignHystrix implements FallbackFactory<BinServiceFeignApi> {

    @Override
    public BinServiceFeignApi create(Throwable cause) {
        return new BinServiceFeignApi() {
            @Override
            public ResultVo<BindataVO[]> listModelBinData(BindataRequest dto) {
                return null;
            }

            @Override
            public ResultVo<PageInfo<BindataVO>> listBindata(BindataRequest dto) {
                return null;
            }

            @Override
            public ResultVo<String> importBindata(MultipartFile file) {
                return null;
            }

            @Override
            public ResultVo<String> saveBindata(BindataVO dto) {
                return null;
            }

            @Override
            public ResultVo<String> deleteBindata(BindataRequest request) {
                return null;
            }

            @Override
            public ResultVo<BindataVO> getBindataByModelNo(String modelNo, String warehouseCode) {
                return null;
            }

            @Override
            public ResultVo<List<Integer>> getBinCountByModelNo(List<String> modelNos, String warehouseCode) {
                return null;
            }

            @Override
            public ResultVo<Map<String, BindataVO>> getBindataByModelNoAndWarehouse(String modelNo, List<String> warehouseList) {
                return null;
            }
            @Override
            public   ResultVo<List<BindataVO>> getBindataByModelNoAnBinType(String modelNo, String binType) {
                return null;
            }
            @Override
            public   ResultVo<Map<String, Boolean>> checkModelIsBin( BindataRequest request){
                return null ;
            }
            @Override
            public ResultVo<List<BindataVO>> getBindataByModelNos(BinDataQueryRequest request) {
                return null;
            }

           /*@Override
           public ResultVo<List<BindataVO>> getBindataInfo(CsModelQryRequest csModelQryRequest) {
               return null;
           }*/

            @Override
            public ResultVo<Boolean> isBinModel(String modelNo) {
                return null;
            }

            @Override
            public void exportBinData(BindataRequest dto) {

            }

            @Override
            public ResultVo<String> updateProductInfo() {
                return null;
            }

            @Override
            public ResultVo<List<BindataVO>> listBinDataForReplQty() {
                return null;
            }

            @Override
            public ResultVo<List<BindataVO>> getBinDataForAutoPreStock(BindataRequest request) {
                return null;
            }

            @Override
            public ResultVo<List<ModelExpFreqVO>> getCustomerModelFreq(CustomerExpFreqRequest request) {
                return null;
            }

            @Override
            public ResultVo<List<ModelExpFreqVO>> getModelExpFreq(ModelExpFreqRequest request) {
                return null;
            }

            @Override
            public ResultVo<List<ModelExpFreqVO>> getModelExpFreqWithRiskLevel(ModelExpFreqRequest request) {
                return null;
            }

            @Override
            public ResultVo<List<ModelExpFreqVO>> getMasterFreq(String modelNo) {
                return null;
            }

            @Override
            public ResultVo<String> refreshBindataCache() {
                return null;
            }
        };
    }

}
