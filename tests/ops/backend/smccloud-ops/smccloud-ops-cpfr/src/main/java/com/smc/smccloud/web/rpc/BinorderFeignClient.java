package com.smc.smccloud.web.rpc;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/10/25 10:54
 */
@Slf4j
@RestController
public class BinorderFeignClient implements BinorderServiceFeignApi {

    @Resource
    private BinorderService binorderService;

    @Resource
    private BinOrderCalcService binOrderCalcService;

    @Resource
    private BinTrialJobManageService binTrialJobManageService;
    @Resource
    private CommonService commonService;

    @Override
    public ResultVo<PageInfo<ModelExpFreqVO>> listModelExpFreq(ModelExpFreqRequest dto) {
        if(dto==null || PublicUtil.isEmpty(dto.getStockcode())){
            return ResultVo.failure("必须输入仓库代码！");
        }
        PageInfo<ModelExpFreqVO> pagelist= binorderService.listModelExpFreq(dto);
        return ResultVo.success(pagelist);
    }

    @Override
    public ResultVo<List<ModelExpDetailVO>> listModeldetail(ModelExpFreqRequest request){
        List<ModelExpDetailVO> list= binorderService.listModeldetail(request);
        if(list==null){
            return ResultVo.failure("没数据！");
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<ModelExpDetailVO>> listModeldetailByJob(ModelExpFreqRequest request){
        List<ModelExpDetailVO> list= binorderService.listModeldetailByJob(request);
        if(list==null){
            return ResultVo.failure("没数据！");
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<BinOrderInventoryInfoVO>> listBinWarehouseStock(String modelNo) {
        return binOrderCalcService.listBinWarehouseStock(modelNo);
    }

    @Override
    public ResultVo<String> calcmodelExpFreq(int type){
        return binorderService.calcmodelExpFreq(type);
    }


    @Override
    public Date getLastPurchaseDlvDate(String modelNo, String warehouseCode){
        return binOrderCalcService.getLastPurchaseDlvDate(modelNo, warehouseCode);
    }
    @Override
    public ResultVo<String> runBinTrialJob(){
        return binTrialJobManageService.runBinTrialJob();
    }

    @Override
    public ResultVo<List<ModelExpFreqVO>>  getModelExpFreqForAvgQty(List<String> modelNoS, String warehouseCode, Integer month){
        try {
            List<ModelExpFreqDO> doList = commonService.getModelExpFreqForAvgQty(modelNoS, warehouseCode, month);
            List<ModelExpFreqVO> voList=  BeanCopyUtil.copyList(doList, ModelExpFreqVO.class);
            return  ResultVo.success(voList);
        }catch (Exception e) {
            log.error("getModelExpFreqForAvgQty -> 执行失败：{}",e);
            return ResultVo.failure("执行失败："+e.getMessage());
        }
    }

    @Override
    public ResultVo<BinOrderCalcVO> newBinOrderCalcId(@RequestBody BinOrderCalcRequestVO vo) {
        BinOrderCalcRequestDTO dto = BeanCopyUtil.copy(vo, BinOrderCalcRequestDTO.class);
        return binorderService.newBinOrderCalcId(dto);
    }
    @Override
    public ResultVo<BinOrderCalcVO> calcBinOrder(@RequestBody BinOrderCalcRequestVO vo) {
        BinOrderCalcRequestDTO dto = BeanCopyUtil.copy(vo, BinOrderCalcRequestDTO.class);
        return binorderService.calcBinOrderSync(dto);
    }

    public ResultVo<String> finishbinordercalc(@RequestParam("id") Long id) {
        return binorderService.finishbinordercalc(id);
    }

    public ResultVo<List<BinOrderCalcVO>> findBinOrderCalcByWarehouseCode(@RequestParam("warehouseCode") String warehouseCode) {
        return binorderService.listBinOrderCalcByWarehouseCode(warehouseCode);
    }
}
