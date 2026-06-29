package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.csstock.*;
import com.smc.smccloud.service.hystrix.ConsignmentStockFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


// url = "httpL://10.116.194.236:8103"

@FeignClient(name = "shareapp-service",
        contextId = "consignmen-stock",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = ConsignmentStockFeignApiHystrix.class)
public interface ConsignmentStockFeignApi {

    /**
     * 查询委托在库型号信息
     * 委托在库型号型号:是否BIN品，是否LOT价，是否拆分，最小包装单位，近半年接单总和，订货频率，客户计数，月均数量，现有在库数，预约数量，在途数量，补库数量，保有月数
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/listModelInfo", method = RequestMethod.POST)
    ResultVo<List<CsModelInfoVO>> listCSModelInfo(@RequestBody CsModelQryRequest csModelQryRequest);

    /**
     * 查询委托在库可补库清单
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/listReplDetail", method = RequestMethod.GET)
    ResultVo<List<CsStockReplenishmentVO>> listReplDetail(@RequestParam("agentNo") String agentNo, @RequestParam("warehouseCode") String warehouseCode);

    /**
     * 生成委托在库补申请号
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/createReplApply", method = RequestMethod.GET)
    ResultVo<String> createReplApply(@RequestParam("agentNo") String agentNo, @RequestParam("warehouseCode") String warehouseCode);

    /**
     * 查询委托在库信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/listCsWarehouse", method = RequestMethod.POST)
    ResultVo<PageInfo<CsWarehouseVO>> listCsWarehouse(@RequestBody CsWarehouseRequest request);

    /**
     * 提交委托在库申请
     * @param request
     * @return
     */
//    @RequestMapping(value = "/shareapp/cs/confirmCsApply", method = RequestMethod.POST)
//    ResultVo<String> confirmCsApply(@RequestBody CsApplyConfirmDTO request);

    /**
     * 查询委托在库库存信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/listCsStockInvenrty", method = RequestMethod.POST)
    ResultVo<PageInfo<CsInventoryVO>> listCsStockInventory(@RequestBody CsInventoryRequestDTO request);


    /**
     * 修改型号货架号
     * @param csStockUpdateLocationDTO
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/updateCsStockModelLocationNo", method = RequestMethod.POST)
    ResultVo<String> updateCsStockModelLocationNo(@RequestBody CsStockUpdateLocationDTO csStockUpdateLocationDTO);

    /**
     * 入库订单明细查询 包括退货
     * @param request
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/listCsImportDetail", method = RequestMethod.POST)
    ResultVo<PageInfo<CsImportDetailVO>> listCsImportDetail(@RequestBody CsImportDetailRequestDTO request);


    /**
     * 收货入库确认
     * @param request
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/confirmReceiveCsStock", method = RequestMethod.POST)
    ResultVo<String> confirmReceiveCsStock(@RequestBody CsStockImportOrderReceiveRequest request);


    /**
     * 出库明细接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/listCsStockExportDetail", method = RequestMethod.POST)
    ResultVo<PageInfo<CsExportDetailVO>> listCsStockExportDetail(@RequestBody CsExportDetailRequestDTO request);

    /**
     * 退货申请明细清单查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/listCsReturnApplyDetail", method = RequestMethod.POST)
    ResultVo<PageInfo<CsReturnDetailVO>> listCsReturnApplyDetail(@RequestBody CsReturnDetailRequestDTO request);

    /**
     * 月次结存查询
     * @return
     */
     @RequestMapping(value = "/shareapp/cs/listMonthBalance", method = RequestMethod.POST)
     ResultVo<PageInfo<CsModelBalanceVO>> listMonthBalance(@RequestBody CsBalcenQryRequest request);

    /**
     * 月结信息确认接口
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/confirmMonthBalance", method = RequestMethod.POST)
    ResultVo<String> confirmMonthBalance(@RequestBody CsMonthBalanceConfirmRequest request);
    /**
     * 月结接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/monthBalance", method = RequestMethod.POST)
    ResultVo<String> monthBalance(@RequestBody CsMonthBalanceConfirmRequest request);



    /**
     * 计算需退货清单
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/listCalcReturn", method = RequestMethod.GET)
    ResultVo<List<CsReturnCalcVo>> listCalcReturn(@RequestParam("agentNo") String agentNo,@RequestParam("warehouseCode") String warehouseCode,@RequestParam("calcType") Integer calcType);


    /**
     * 计算需退货清单
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/pageListCalcReturn", method = RequestMethod.POST)
    ResultVo<PageInfo<CsReturnCalcVo>> pageListCalcReturn(@RequestBody CalcReturnRequest request);

    /**
     * 申请补货
     * @param dto
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/applyRepl", method = RequestMethod.POST)
    ResultVo<String> applyRepl(@Valid @RequestBody CsApplyDTO dto);

    /**
     * 出库确认
     * @param request
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/confirmExpOrder",method = RequestMethod.POST)
    ResultVo<String> confirmExpOrder(@RequestBody CsExpDetailConfirmRequest request);

    /**
     * 导入委托在库收货数据清单
     * @param csImportDataDTOS
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/addImpData", method = RequestMethod.POST)
    ResultVo<String> addImpData(@RequestBody List<CsImportDataDTO> csImportDataDTOS);

    /**
     * 写入委托在库出库表 cs_expdetail
     * @param csExpDataDOS
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/addExpData", method = RequestMethod.POST)
    ResultVo<String> addExpData(@Valid @RequestBody List<CsExpdetailVO> csExpDataDOS);


    /**
     * 导出库房清单
     * @param request
     */
    @RequestMapping(value = "/shareapp/cs/exportWareHouseData", method = RequestMethod.POST)
    void exportWareHouseData(@RequestBody CsWarehouseRequest request);

    /**
     * 根据出库订单反扣入库订单的剩余数量,按所有没有扣数的出库订单
     */
    @GetMapping(value = "/shareapp/cs/calcImpOrderLeftQty")
    void calcImpOrderLeftQty();

    /**
     * 退货申请生成退货订单
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/confirmCsReturnApply", method = RequestMethod.GET)
    ResultVo<String> confirmCsReturnApply(@RequestParam("applyId") Integer applyId);


    /**
     * 根据入库数量和库存数量 反算impData表的出库数量
     */
    @RequestMapping(value = "/shareapp/cs/calCsImpExpQty", method = RequestMethod.GET)
    ResultVo<String> calCsImpExpQty();

    @RequestMapping(value = "/shareapp/cs/getCsStockSetting", method = RequestMethod.GET)
    ResultVo<CsStockSettingVO> getCsStockSetting(@RequestParam("agentNo") String agentNo,@RequestParam("warehouseCode") String warehouseCode,@RequestParam("modelNo") String modelNo);

    @RequestMapping(value = "/shareapp/cs/sysReturnOrderToImpData", method = RequestMethod.GET)
    ResultVo<String> sysReturnOrderToImpData();

    @RequestMapping(value = "/shareapp/cs/getCanUseEAmount", method = RequestMethod.GET)
    ResultVo<CsEAmountDTO> getCanUseEAmount(@RequestParam("customerNo") String customerNo);

    /**
     * 签收货物
     * @param dto
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/RecieveGoodsByInvoice", method = RequestMethod.POST)
    ResultVo<String> recieveGoodsByDelivery(@RequestBody CsReceiveGoodsDTO dto);

    @RequestMapping(value = "/shareapp/cs/RecieveGoodsByInvoiceList", method = RequestMethod.POST)
    ResultVo<String> recieveGoodsByDeliveryList(@RequestBody List<CsReceiveGoodsDTO> dtoList);

    /**
     * 同步发货数据到cost
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/syncCsImpDataToCost", method = RequestMethod.GET)
    ResultVo<String> syncCsImpDataToCost();

    @RequestMapping(value = "/shareapp/cs/listCsImpdata", method = RequestMethod.GET)
    ResultVo<List<CsImpdataVO>> listCsImpdata();

    @RequestMapping(value = "/shareapp/cs/updateImpDataRoId", method = RequestMethod.POST)
    ResultVo<String> updateImpDataRoId(@RequestBody List<CsImpdataVO> list);

    @RequestMapping(value = "/shareapp/cs/importInvoiceData", method = RequestMethod.GET)
    ResultVo<String> importInvoiceData();

    @RequestMapping(value = "/shareapp/cs/cancelCsExpDetailByOrderNo", method = RequestMethod.POST)
    ResultVo<String> cancelCsExpDetailByOrderNo(@RequestBody List<String> orderNos);

    /**
     * 将财务已开票的退货单推送至WMS
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/impInvoiceResult", method = RequestMethod.GET)
    ResultVo<String> impInvoiceResult();

    @RequestMapping(value = "/shareapp/cs/dealReturnOrderToMH", method = RequestMethod.GET)
    ResultVo<String> dealReturnOrderToMH();

    @RequestMapping(value = "/shareapp/cs/calcBalance", method = RequestMethod.GET)
    ResultVo<String> calcBalance(@RequestParam("monthDate") Date monthDate);

    /**
     * 同步退货开票信息
     * @return
     */
    @RequestMapping(value = "/shareapp/cs/syncReturnInvoiceInfo", method = RequestMethod.GET)
    ResultVo<String> syncReturnInvoiceInfo();
}
