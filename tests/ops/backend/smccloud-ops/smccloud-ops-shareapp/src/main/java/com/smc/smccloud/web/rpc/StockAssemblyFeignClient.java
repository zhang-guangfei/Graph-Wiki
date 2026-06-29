package com.smc.smccloud.web.rpc;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.model.stockassembly.StockAssemblyApplyDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyItemDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyRequest;
import com.smc.smccloud.model.stockassembly.TransferResult;
import com.smc.smccloud.service.StockAssemblyFeignApi;
import com.smc.smccloud.service.StockAssemblyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-09-27 13:16
 * Description: 组换调库申请 Feign Client
 */
@Slf4j
@RestController
public class StockAssemblyFeignClient implements StockAssemblyFeignApi {

    @Resource
    private StockAssemblyService stockAssemblyService;
    //    @Resource
//    private ImpdataAdjustService impdataAdjustService;


    @Override
    public ResultVo<String> createStockAssemblyApply(StockAssemblyApplyDTO createDto) {
        return stockAssemblyService.createStockAssemblyApply(createDto);
    }

    // Add by DengDengHui, 2022-10-26 for bug-8395
    @Override
    public ResultVo<List<TransferResult>> handleSMSInStockApply(StockAssemblyApplyDTO createDto) {
        // 保存门户客户调库申请
        log.info("createStockAssemblyApply data = {}", createDto);
        ResultVo<String> applyResult = stockAssemblyService.createStockAssemblyApply(createDto);
        log.info("createStockAssemblyApply {} saveResult => {}", createDto.getApplyNo(), applyResult);
        if (!applyResult.isSuccess()) {
            return ResultVo.failure(applyResult.getMessage());
        }

        // 执行处理
        ResultVo<List<TransferResult>> handleResult = stockAssemblyService.handleSMSInStockApply(createDto.getApplyNo());
        log.info("handleSMSInStockApply {} => {}", createDto.getApplyNo(), handleResult);
        return handleResult;
    } // End

//    @Override
//    public ResultVo<String> handleSMSInStockApply(StockAssemblyApplyDTO createDto) {
//        // 保存调库申请
//        log.info("createStockAssemblyApply data = {}", createDto);
//        ResultVo<String> applyResult = stockAssemblyService.createStockAssemblyApply(createDto);
//        log.info("createStockAssemblyApply result = {}", applyResult);
//        if (!applyResult.isSuccess()) {
//            return applyResult;
//        }
//
//        // 执行处理
//        StockAssemblyHandleDTO handleDTO = new StockAssemblyHandleDTO();
//        handleDTO.setApplyIds(Collections.singletonList(Long.parseLong(applyResult.getData())));
//        handleDTO.setHandleType("3");
//        ResultVo<String> handleResult = stockAssemblyService.handleApply(handleDTO);
//        log.info("handleApply handleResult = {}", handleResult);
//        if (!handleResult.isSuccess()) {
//            throw new BusinessException(handleResult.getMessage());
//        }
//        return ResultVo.success("处理成功");
//    }

    @Override
    public ResultVo<String> importAssemblyCostData() {
        return stockAssemblyService.sendAssemblyApplyToCost(); // 新版处理方法 Add by Dengdenghui 2022-11-29 bug-8822
    }

    @Override
    public ResultVo<String> updateAssemblyStatus(String applyNo, Boolean result) {
        return stockAssemblyService.updateAssemblyStatus(applyNo, result);
    }
    @Override
    public ResultVo<PageInfo<StockAssemblyItemDTO>> listStockAssemblyApplyDetail(StockAssemblyRequest request) {
        return stockAssemblyService.listStockAssemblyApplyDetail(request);
    }
    @Override
    public ResultVo<List<InventoryForAdjustDto>> getAssemblyInDataForWMS(String applyNo ) {
        return stockAssemblyService.getAssemblyDataForWMS(applyNo,false);
    }

}
