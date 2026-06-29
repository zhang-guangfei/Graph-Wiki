package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.csstock.*;
import com.smc.smccloud.service.ConsignmentStockFeignApi;
import feign.hystrix.FallbackFactory;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 17:45
 */
public class ConsignmentStockFeignApiHystrix implements FallbackFactory<ConsignmentStockFeignApi> {


    @Override
    public ConsignmentStockFeignApi create(Throwable throwable) {
        return new ConsignmentStockFeignApi() {
            @Override
            public ResultVo<List<CsModelInfoVO>> listCSModelInfo(CsModelQryRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<CsStockReplenishmentVO>> listReplDetail(String agentNo, String warehouseCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> createReplApply(String agentNo, String warehouseCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<CsWarehouseVO>> listCsWarehouse(CsWarehouseRequest request) {
                return ResultVo.failure("服务降级");
            }

//            @Override
//            public ResultVo<String> confirmCsApply(CsApplyConfirmDTO request) {
//                return null;
//            }

            @Override
            public ResultVo<PageInfo<CsInventoryVO>> listCsStockInventory(CsInventoryRequestDTO request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> updateCsStockModelLocationNo(CsStockUpdateLocationDTO csStockUpdateLocationDTO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<CsImportDetailVO>> listCsImportDetail(CsImportDetailRequestDTO request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> confirmReceiveCsStock(CsStockImportOrderReceiveRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<CsExportDetailVO>> listCsStockExportDetail(CsExportDetailRequestDTO request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<CsReturnDetailVO>> listCsReturnApplyDetail(CsReturnDetailRequestDTO request) {
                return null;
            }

            @Override
            public ResultVo<PageInfo<CsModelBalanceVO>> listMonthBalance(CsBalcenQryRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> confirmMonthBalance(CsMonthBalanceConfirmRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> monthBalance(CsMonthBalanceConfirmRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<CsReturnCalcVo>> listCalcReturn(String agentNo, String warehouseCode,Integer calcType) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<CsReturnCalcVo>> pageListCalcReturn(CalcReturnRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> applyRepl(CsApplyDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> confirmExpOrder(CsExpDetailConfirmRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> addImpData(List<CsImportDataDTO> csImportDataDTOS) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> addExpData(@Valid List<CsExpdetailVO> csExpDataDOS) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public void exportWareHouseData(CsWarehouseRequest request) {
            }

            @Override
            public void calcImpOrderLeftQty() {

            }

            @Override
            public ResultVo<String> confirmCsReturnApply(Integer applyId) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> calCsImpExpQty() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<CsStockSettingVO> getCsStockSetting(String agentNo, String warehouseCode, String modelNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> sysReturnOrderToImpData() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<CsEAmountDTO> getCanUseEAmount(String customerNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> recieveGoodsByDelivery(CsReceiveGoodsDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> recieveGoodsByDeliveryList(List<CsReceiveGoodsDTO> dtoList) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> syncCsImpDataToCost() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<CsImpdataVO>> listCsImpdata() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> updateImpDataRoId(List<CsImpdataVO> list) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> importInvoiceData() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> cancelCsExpDetailByOrderNo(List<String> orderNos) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> impInvoiceResult() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> dealReturnOrderToMH() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> calcBalance(Date monthDate) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> syncReturnInvoiceInfo() {
                return ResultVo.failure("服务降级");
            }

        };
    }
}
