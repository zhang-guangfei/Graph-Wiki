package com.smc.smccloud.web.rpc;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.model.csstock.*;
import com.smc.smccloud.service.ConsignmentStockFeignApi;
import com.smc.smccloud.service.CsStockApplyService;
import com.smc.smccloud.service.OpsWarehouseService;
import com.smc.smccloud.service.ReturnOrderService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 委托在库接口
 */
@RestController
public class ConsignmentStockFeignClient implements ConsignmentStockFeignApi {

    @Resource
    private CsStockApplyService csStockApplyService;
    @Resource
    private ReturnOrderService returnOrderService;
    @Resource
    private OpsWarehouseService warehouseService;

    @Override
    public ResultVo<List<CsModelInfoVO>> listCSModelInfo(CsModelQryRequest csModelQryRequest) {
        return csStockApplyService.listCSModelInfo(csModelQryRequest);
    }

    @Override
    public ResultVo<List<CsStockReplenishmentVO>> listReplDetail(String agentNo, String warehouseCode) {
        return csStockApplyService.listReplDetail(agentNo, warehouseCode);
    }

    @Override
    public ResultVo<String> createReplApply(String agentNo, String warehouseCode) {
        return csStockApplyService.createReplApply(agentNo, warehouseCode);
    }

    @Override
    public ResultVo<PageInfo<CsWarehouseVO>> listCsWarehouse(CsWarehouseRequest request) {
        return warehouseService.listCsWarehouse(request);
    }

/*    @Override
    public ResultVo<String> confirmCsApply(CsApplyConfirmDTO request) {
        return csStockApplyService.confirmCsApply(request);
    }*/

    @Override
    public ResultVo<PageInfo<CsInventoryVO>> listCsStockInventory(CsInventoryRequestDTO request) {
        return csStockApplyService.listCsStockInventory(request, request.getPage());
    }

    @Override
    public ResultVo<String> updateCsStockModelLocationNo(CsStockUpdateLocationDTO csStockUpdateLocationDTO) {
        return csStockApplyService.updateCsStockModelLocationNo(csStockUpdateLocationDTO);
    }

    @Override
    public ResultVo<PageInfo<CsImportDetailVO>> listCsImportDetail(CsImportDetailRequestDTO request) {
        return csStockApplyService.listCsImportDetail(request);
    }

    @Override
    public ResultVo<String> confirmReceiveCsStock(CsStockImportOrderReceiveRequest request) {

        return csStockApplyService.confirmReceiveCsStock(request);
    }

    @Override
    public ResultVo<PageInfo<CsExportDetailVO>> listCsStockExportDetail(CsExportDetailRequestDTO request) {
        return csStockApplyService.listCsExportDetail(request);
    }

    @Override
    public ResultVo<PageInfo<CsReturnDetailVO>> listCsReturnApplyDetail(CsReturnDetailRequestDTO request) {
        return csStockApplyService.listCsReturnApplyDetail(request);
    }

    @Override
    public ResultVo<PageInfo<CsModelBalanceVO>> listMonthBalance(CsBalcenQryRequest request) {
        return csStockApplyService.listMonthBalance(request);
    }

    @Override
    public ResultVo<String> confirmMonthBalance(CsMonthBalanceConfirmRequest request) {
        return csStockApplyService.confirmMonthBalance(request);
    }

    @Override
    public ResultVo<String> monthBalance(CsMonthBalanceConfirmRequest request) {
        return csStockApplyService.monthBalance(request);
    }

    @Override
    public ResultVo<List<CsReturnCalcVo>> listCalcReturn(String agentNo, String warehouseCode, Integer calcType) {
        return csStockApplyService.listCalcReturn(agentNo, warehouseCode, calcType);
    }

    @Override
    public ResultVo<PageInfo<CsReturnCalcVo>> pageListCalcReturn(CalcReturnRequest request) {
        return csStockApplyService.pageListCalcReturn(request);
    }

    @Override
    public ResultVo<String> applyRepl(CsApplyDTO dto) {
        return csStockApplyService.applyRepl(dto);
    }

    @Override
    public ResultVo<String> confirmExpOrder(CsExpDetailConfirmRequest request) {
        return csStockApplyService.confirmExpOrder(request);
    }

    @Override
    public ResultVo<String> addImpData(List<CsImportDataDTO> csImportDataDTOS) {
        return csStockApplyService.addImpData(csImportDataDTOS);
    }

    @Override
    public ResultVo<String> addExpData(@Valid List<CsExpdetailVO> csExpDataDOS) {
        return csStockApplyService.addExpData(csExpDataDOS);
    }

    @Override
    public void exportWareHouseData(CsWarehouseRequest request) {
        csStockApplyService.exportWareHouseData(request);
    }

    @Override
    public void calcImpOrderLeftQty() {
        csStockApplyService.calcImpOrderLeftQty();
    }

    @Override
    public ResultVo<String> confirmCsReturnApply(Integer applyId) {
        return csStockApplyService.confirmCsReturnApply(applyId);
    }

    @Override
    public ResultVo<String> calCsImpExpQty() {
        return csStockApplyService.calCsImpExpQty();
    }

    @Override
    public ResultVo<CsStockSettingVO> getCsStockSetting(String agentNo, String warehouseCode, String modelNo) {
        CsStockSettingDO stockSettingDO = csStockApplyService.getCsStockSetting(agentNo, warehouseCode, modelNo);
        CsStockSettingVO settingVO = null;
        if (stockSettingDO != null) {
            settingVO = BeanCopyUtil.copy(stockSettingDO, CsStockSettingVO.class);
        }
        return ResultVo.success(settingVO);
    }

    @Override
    public ResultVo<String> sysReturnOrderToImpData() {
        return returnOrderService.sysReturnOrderToImpData();
    }

    @Override
    public ResultVo<CsEAmountDTO> getCanUseEAmount(String customerNo) {
        return csStockApplyService.getCanUseEAmount(customerNo);
    }

    @Override
    public ResultVo<String> recieveGoodsByDelivery(CsReceiveGoodsDTO dto) {
        return csStockApplyService.recieveGoodsByDelivery(dto);
    }

    @Override
    public ResultVo<String> recieveGoodsByDeliveryList(List<CsReceiveGoodsDTO> dtoList) {
        return csStockApplyService.recieveGoodsByDeliveryList(dtoList);
    }

    @Override
    public ResultVo<String> syncCsImpDataToCost() {
        return csStockApplyService.syncCsImpDataToCost();
    }

    @Override
    public ResultVo<List<CsImpdataVO>> listCsImpdata() {
        return csStockApplyService.listCsImpdata();
    }

    @Override
    public ResultVo<String> updateImpDataRoId(List<CsImpdataVO> list) {
        return csStockApplyService.updateImpDataRoId(list);
    }

    @Override
    public ResultVo<String> importInvoiceData() {
        return returnOrderService.importInvoiceData();
    }

    @Override
    public ResultVo<String> cancelCsExpDetailByOrderNo(List<String> orderNos) {
        return csStockApplyService.cancelCsExpDetailByOrderNo(orderNos);
    }

    @Override
    public ResultVo<String> impInvoiceResult() {
        return returnOrderService.impInvoiceResult();
    }

    @Override
    public ResultVo<String> dealReturnOrderToMH() {
        return returnOrderService.dealReturnOrderToMH();
    }

    @Override
    public ResultVo<String> calcBalance(Date monthDate) {
        return csStockApplyService.calcBalance(monthDate);
    }

    @Override
    public ResultVo<String> syncReturnInvoiceInfo() {
        return returnOrderService.syncReturnInvoiceInfo();
    }
}
