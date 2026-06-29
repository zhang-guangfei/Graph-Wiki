package com.smc.smccloud.web.rpc;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.bindata.BinDataQueryRequest;
import com.smc.smccloud.model.bindata.BindataRequest;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.model.binorder.CustomerExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.service.BinServiceFeignApi;
import com.smc.smccloud.service.BindataService;
import com.smc.smccloud.service.BinorderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author edp02 @Date 2021/10/8 15:18
 */
@RestController
@Slf4j
public class BindataFeignClient implements BinServiceFeignApi {

    @Resource
    private BindataService bindataService;
    @Resource
    private BinorderService binorderService;

    @Override
    public ResultVo<BindataVO[]> listModelBinData(BindataRequest dto) {

        if (dto.getModelNos()==null && dto.getModelNos().size()==0) {
            return ResultVo.failure("型号不能为空！");
        }
        List<BindataVO> list = bindataService.listModelBinData(dto);
        if (list == null || list.isEmpty()) {
            return ResultVo.failure("没数据！");
        }
        BindataVO[] items = list.toArray(new BindataVO[0]);
        return ResultVo.success(items);
    }

    @Override
    public ResultVo<PageInfo<BindataVO>> listBindata(BindataRequest dto) {
        PageInfo<BindataVO> list = bindataService.listBindata(dto);
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<String> importBindata(MultipartFile file) {
        return binorderService.importBindata(file);
    }

//    @Override
//    public ResultVo<String> checkImpBindataFileStatus(String key) {
//        return null;
//    }

    @Override
    public ResultVo<String> saveBindata(BindataVO dto) {
        return bindataService.saveBindata(dto);
    }

    @Override
    public ResultVo<String> deleteBindata(BindataRequest request) {
        return bindataService.deleteBindata(request.getIds());
    }

    @Override
    public ResultVo<BindataVO> getBindataByModelNo(String modelNo,String warehouseCode) {
        return bindataService.getBindataByModelNo(modelNo, warehouseCode);
    }

    @Override
    public ResultVo<List<Integer>> getBinCountByModelNo(List<String> modelNos, String warehouseCode) {
        return bindataService.getBinCountByModelNo(modelNos, warehouseCode);
    }

    @Override
    public ResultVo<Map<String, BindataVO>> getBindataByModelNoAndWarehouse(String modelNo, List<String> warehouseList) {
        return bindataService.getBindataByModelNoAndWarehouse(modelNo, warehouseList);
    }
    @Override
    public ResultVo<List<BindataVO>> getBindataByModelNoAnBinType(String modelNo, String binType) {
        return bindataService.getBindataByModelNoAnBinType(modelNo, binType);
    }

    @Override
    public ResultVo<List<BindataVO>> getBindataByModelNos(BinDataQueryRequest request) {
        return bindataService.getBindataByModelNos(request);
    }
    @Override
    public ResultVo<Map<String, Boolean>> checkModelIsBin(BindataRequest request){
        return bindataService.checkModelIsBin(request.getModelNos(), request.getWarehouseCodes());
    }
    @Override
    public ResultVo<Boolean> isBinModel(String modelNo) {
        return bindataService.isBinModel(modelNo);
    }

    @Override
    public void exportBinData(BindataRequest dto){
        bindataService.exportBinData(dto);
    }

    @Override
    public ResultVo<String> updateProductInfo(){
        return bindataService.updateProductInfo();
    }

    @Override
    public ResultVo<List<BindataVO>> listBinDataForReplQty(){
        return bindataService.listBinDataForReplQty();
    }

    @Override
    public ResultVo<List<BindataVO>> getBinDataForAutoPreStock(BindataRequest request) {
        return bindataService.getBinDataForAutoPreStock(request);
    }

    @Override
    public ResultVo<List<ModelExpFreqVO>> getModelExpFreq(ModelExpFreqRequest freqRequest) {
        return  bindataService.getModelExpFreq(freqRequest);
    }

    @Override
    public ResultVo<List<ModelExpFreqVO>> getModelExpFreqWithRiskLevel(ModelExpFreqRequest request) {
        return  bindataService.getModelExpFreqWithRiskLevel(request);
    }

    /**
     * bug11986,订单删除需要增加风险验证，新增获取所有大仓的 AvgQtyOf8字段
     * @param modelNo
     * @return
     */
    @Override
    public ResultVo<List<ModelExpFreqVO>> getMasterFreq(String modelNo) {
        return bindataService.getMasterFreq(modelNo);
    }


    @Override
    public ResultVo<String> refreshBindataCache() {
        return null;
    }

    /*@Override
    public ResultVo<List<BindataVO>> getBindataInfo(CsModelQryRequest csModelQryRequest) {
        return bindataService.getBindataInfo(csModelQryRequest);
    }*/

    @Override
    public ResultVo<List<ModelExpFreqVO>> getCustomerModelFreq(CustomerExpFreqRequest request) {
        return bindataService.getCustomerModelFreq(request);
    }
}
