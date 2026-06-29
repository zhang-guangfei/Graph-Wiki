package com.smc.smccloud.web.rpc;

import com.sales.ops.dto.prepareOrder.PrepareOrderTransferDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-11-16 09:46
 * Description:
 */
@Slf4j
@RestController
public class PreStockFeignClient implements PreStockFeignApi {

    @Resource
    private PreStockService preStockService;

    @Resource
    private PreStockNewService preStockNewService;

    @Resource
    private PreOrderAccountService preOrderAccountService;

    @Resource
    private PreOrderAccountHandService preOrderAccountHandService;

    @Override
    public ResultVo<String> createPreStockApply(PreStockApplyDetailDTO createDto) {
        return preStockService.createPreStockApply(createDto);
    }

    @Override
    public ResultVo<PreStockApplyDetailDTO> findPreStockApplyByNo(String applyNo) {
        return preStockService.findPreStockApplyByNo(applyNo);
    }

    @Override
    public ResultVo<List<PreStockDetailDTO>> findPreStockDetailByNo(Long applyId, String modelNo) {
        return preStockService.findPreStockDetailByNo(applyId, modelNo);
    }

    @Override
    public ResultVo<String> handleSMSPreStockApply(PreStockApplyDetailDTO createDto) {
        // 保存申请信息
        ResultVo<String> applyResult = this.createPreStockApply(createDto);
        log.info("保存门户备库申请: {} >>> {}", createDto.getApplyNo(), applyResult);
        if (!applyResult.isSuccess()) {
            return applyResult;
        }
        createDto.setId(Long.parseLong(applyResult.getData()));
        return preStockService.handleSMSPreStockApply(createDto);
    }

    @Override
    public ResultVo<String> autoProcessInterceptedApplyDetail() {
        try {
            preStockService.autoProcessInterceptedApplyDetail();
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            return ResultVo.failure("处理失败, " + e.getMessage());
        }
        return ResultVo.success("处理成功");
    }

    @Override
    public ResultVo<String> handleTransferByAuto() {
        try {
            preOrderAccountService.handleTransferByAuto();
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            return ResultVo.failure("先行在库决算自动调拨失败, " + e.getMessage());
        }
        return ResultVo.success("先行在库决算自动调拨成功");
    }

    @Override
    public ResultVo<String> crePreOrderAccountData() {
        return preOrderAccountHandService.crePreOrderAccountDataNew();
    }

    @Override
    public ResultVo<String> updatePreAccountDetail(PreOrderAccountDetailVO info) {
        return preOrderAccountHandService.updatePreAccountDetail(info);
    }

    @Override
    public ResultVo<String> updatePreOrderAccountByInventoryId(Long inventoryId) {
        return preOrderAccountHandService.calPreOrderAccountQty(inventoryId);
    }

    @Override
    public ResultVo<String> insertPreOrderAccountApplyDetailData(PreOrderAccountDetailVO preOrderAccountDetailVO) {
        return preOrderAccountHandService.insertPreOrderAccountApplyDetailData(preOrderAccountDetailVO);
    }

    @Override
    public ResultVo<String> autoHandleYqzPre() {
        return preOrderAccountHandService.autoHandleYqzPre();
    }

    @Override
    public ResultVo<String> rejectPrepareOrderUpPreStockStatus(RejectPrepareOrderUpStockStatusDto dto) {
        return preStockNewService.rejectPrepareOrderUpPreStockStatus(dto);
    }

    @Override
    public ResultVo<String> prepareOrderTransferWithPresotckResult(PrepareOrderTransferDto dto) {
        return preStockNewService.prepareOrderTransferWithPresotckResult(dto);
    }

    @Override
    public ResultVo<List<ShikomiCallbackDTO>> getShikomiStockData(List<String> applyNos) {
        return preStockService.getShikomiStockData(applyNos);
    }

    @Override
    public ResultVo<String> purchaseOrderCancelHandle(String orderNo) {
        return preStockService.purchaseOrderCancelHandle(orderNo);
    }

    @Override
    public ResultVo<String> autoCallbackPortal() {
        return preStockService.autoCallbackPortal();
    }

    @Override
    public ResultVo<String> callBackResultToPortalByBatchNo(String batchNo) {
        return preStockService.callBackResultToPortalByBatchNo(batchNo);
    }

}
