package com.smc.smccloud.service.hystrix;

import com.sales.ops.dto.prepareOrder.PrepareOrderTransferDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.service.PreStockFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-11-16 09:40
 * Description:
 */
@Component
public class PreStockFeignApiHystrix implements FallbackFactory<PreStockFeignApi> {

    @Override
    public PreStockFeignApi create(Throwable cause) {
        return new PreStockFeignApi() {
            @Override
            public ResultVo<String> createPreStockApply(PreStockApplyDetailDTO createDto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PreStockApplyDetailDTO> findPreStockApplyByNo(String applyNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<PreStockDetailDTO>> findPreStockDetailByNo(Long applyId, String modelNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> handleSMSPreStockApply(PreStockApplyDetailDTO createDto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoProcessInterceptedApplyDetail() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> handleTransferByAuto() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> crePreOrderAccountData() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> updatePreAccountDetail(PreOrderAccountDetailVO info) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> updatePreOrderAccountByInventoryId(Long inventoryId) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> insertPreOrderAccountApplyDetailData(PreOrderAccountDetailVO preOrderAccountDetailVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoHandleYqzPre() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> rejectPrepareOrderUpPreStockStatus(RejectPrepareOrderUpStockStatusDto dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> prepareOrderTransferWithPresotckResult(PrepareOrderTransferDto dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<ShikomiCallbackDTO>> getShikomiStockData(List<String> applyNos) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> purchaseOrderCancelHandle(String orderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> autoCallbackPortal() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> callBackResultToPortalByBatchNo(String batchNo) {
                return ResultVo.failure("服务降级");
            }
        };
    }
}
