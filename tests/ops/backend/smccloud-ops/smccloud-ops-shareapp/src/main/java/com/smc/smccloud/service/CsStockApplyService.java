package com.smc.smccloud.service;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.csstock.*;
import com.smc.smccloud.model.returnorder.ReturnOrderDO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 委托在库服务类
 *
 * @author wsf
 * @since 2021-11-03
 */
public interface CsStockApplyService {

    /**
     * 查询委托在库申请
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<CsApplyDO>> listCsStockApply(CsStockApplyRequest request);


    /**
     * 查询委托在库申请明细
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<CsApplyDetailDO>> listCsStockApplyDetail(CsApplyDetailRequest request);

    /**
     * 查询委托在库型号设置清单
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<CsStockSettingDO>> listCsStockSetting(CsStockSettingRequest request);

    /**
     * 更新委托在库型号设置信息
     *
     * @param request
     * @return
     */
    ResultVo<String> updateCsStockSetting(CsStockSettingDTO request);

    /**
     * 委托在库型号启停使用备库
     *
     * @param agentNo
     * @param warehouseCode
     * @param modelNo
     * @return
     */
    ResultVo<String> updateCsSettingModelStatus(String agentNo, String warehouseCode, String modelNo, Integer status);

    /**
     * 计算并查询需补货清单
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    ResultVo<List<CsStockReplenishmentVO>> listReplDetail(String agentNo, String warehouseCode);


    /**
     * 生成补货补货申请
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    ResultVo<String> createReplApply(String agentNo, String warehouseCode);

    /**
     * 删除委托申请清单子项
     *
     * @param applyId
     * @param modelNo
     * @return
     */
    ResultVo<String> removeDetail(Long applyId, String modelNo);

    /**
     * 删除委托在库（更变状态）
     *
     * @param applyId
     * @return
     */
    ResultVo<String> removeApply(Long applyId);

    /*
     * 提交委托在库申请（发送并生成订单）
     *
     * @param request
     * @return
     */
    //ResultVo<String> confirmCsApply(CsApplyConfirmDTO request);

    /**
     * 更新委托在库库房使用状态
     *
     * @param warehouseCode
     * @param status
     * @return
     */
    ResultVo<String> updateCsWarehouseStatus(String warehouseCode, Integer status);

    /**
     * 导入委托在库型号库存清单
     *
     * @param file
     * @return
     */
    ResultVo<String> importCsStockSettingData(MultipartFile file, Integer type);

    /**
     * 删除委托在库型号清单
     *
     * @param id
     * @return
     */
    ResultVo<String> deleteCsStockSettingById(Long id);


    /**
     * 新增委托在库
     *
     * @param csApplyDetailDTO
     * @return
     */
    ResultVo<String> addDetail(CsApplyDetailDTO csApplyDetailDTO);


    /**
     * 修改委托申请明细项
     *
     * @param csApplyDetailDTO
     * @return
     */
    ResultVo<String> updateDetail(CsApplyDetailDTO csApplyDetailDTO);


    /**
     * 更新委托在库型号货架位
     *
     * @param csStockUpdateLocationDTO
     * @return
     */
    ResultVo<String> updateCsStockModelLocationNo(CsStockUpdateLocationDTO csStockUpdateLocationDTO);

    /**
     * 入库清单明细查询
     *
     * @param requestDTO
     * @return
     */
    ResultVo<PageInfo<CsImportDetailVO>> listCsImportDetail(CsImportDetailRequestDTO requestDTO);

    /**
     * 收货确认
     *
     * @param request
     * @return
     */
    ResultVo<String> confirmReceiveCsStock(CsStockImportOrderReceiveRequest request);

    /**
     * 出库确认
     */
    ResultVo<String> confirmExpOrder(CsExpDetailConfirmRequest request);

    /**
     * 出库清单明细查询
     *
     * @param requestDTO
     * @return
     */
    ResultVo<PageInfo<CsExportDetailVO>> listCsExportDetail(CsExportDetailRequestDTO requestDTO);


    /**
     * 查询委托在库库存信息
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<CsInventoryVO>> listCsStockInventory(CsInventoryRequestDTO request, Page page);

    /**
     * 月次结存
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<CsModelBalanceVO>> listMonthBalance(CsBalcenQryRequest request);


    /**
     * 同步更新型号信息(每晚凌晨闲时)（是否BIN品，是否LOT价，是否拆分，最小包装单位，近半年接单总和，订货频率，客户计数，月均数量，现有在库数，预约数量，在途数量，补库数量，保有月数）
     * 同步到cs_stock_setting
     *
     * @return
     */
    ResultVo<String> syncCsModelInfo();

    /**
     * 同步数据到委托在库入库清单表(每晚凌晨闲时)
     * 同步ops_core.expdetail to ops_sharedb.cs_impdata
     *
     * @return
     */
    ResultVo<String> syncCsImportData();

    /**
     * 委托在库退货计算数据
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    ResultVo<List<CsReturnCalcVo>> listCalcReturn(String agentNo, String warehouseCode, Integer calcType);

    ResultVo<PageInfo<CsTmpReturnVO>> listCalcReturnMaster(CsTmpReturnDTO dto);

    /**
     * 委托在库退货计算数据
     *
     * @return
     */
    ResultVo<PageInfo<CsReturnCalcVo>> pageListCalcReturn(CalcReturnRequest request);

    /**
     * 生成退货数据
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    ResultVo<String> createReturnApply(String agentNo, String warehouseCode);


    /**
     * 提交委托在库退货申请（更变状态）
     *
     * @param applyId
     * @return
     */
    ResultVo<String> confirmCsReturnApply(Integer applyId);

    /**
     * 查询退货申请清单
     *
     * @param requestDTO
     * @return
     */
    ResultVo<PageInfo<CsReturnApplyVO>> listReturnApply(CsReturnApplyRequestDTO requestDTO);

    /**
     * 退货申请明细查询
     *
     * @param requestDTO
     * @return
     */
    ResultVo<PageInfo<CsReturnDetailVO>> listCsReturnApplyDetail(CsReturnDetailRequestDTO requestDTO);

    /**
     * 根据申请号退货明细清单
     *
     * @param applyId
     * @return
     */
    List<CsReturnDetailVO> listCsReturnDetailByApplyId(Integer applyId);

    /**
     * 打印退货单
     *
     * @param applyId
     * @return
     */
    ResultVo<PrintCsReturnDTO> printCsReturnByApplyId(Integer applyId);


    /**
     * 打印月结报表
     *
     * @param request
     * @param response
     */
    void printBalance(CsBalcenQryRequest request, HttpServletResponse response);

    /**
     * 查询委托在库型号信息
     * 委托在库型号型号:是否BIN品，是否LOT价，是否拆分，最小包装单位，近半年接单总和，订货频率，客户计数，月均数量，现有在库数，预约数量，在途数量，补库数量，保有月数
     *
     * @param csModelQryRequest
     * @return
     */
    ResultVo<List<CsModelInfoVO>> listCSModelInfo(CsModelQryRequest csModelQryRequest);

    /**
     * 根据型号和库房号查询备库数
     */
    ResultVo<CsStockSettingDTO> fingInitStockQtyByModelNo(String modelNo, String stockCode);

    /**
     * 申请补货
     *
     * @param dto
     * @return
     */
    ResultVo<String> applyRepl(CsApplyDTO dto);


    /**
     * 月结确认
     */
    ResultVo<String> confirmMonthBalance(CsMonthBalanceConfirmRequest request);

    /**
     * 月结
     *
     * @param request
     * @return
     */
    ResultVo<String> monthBalance(CsMonthBalanceConfirmRequest request);

    /**
     * 写入委托在库收货表
     *
     * @param csImpdataDOS
     * @return
     */
    ResultVo<String> addImpData(List<CsImportDataDTO> csImpdataDOS);

    /**
     * 写入委托在库出库表 cs_expdetail
     *
     * @param csExpDataDOS
     * @return
     */
    ResultVo<String> addExpData(List<CsExpdetailVO> csExpDataDOS);

    /**
     * 导出库房清单
     *
     * @param request
     */
    void exportWareHouseData(CsWarehouseRequest request);

    /**
     * 根据出库订单反扣入库订单的剩余数量
     *
     * @param expOrderNo
     */
    void calcImpOrderLeftQty(String expOrderNo);

    /**
     * 根据出库订单反扣入库订单的剩余数量,按所有没有扣数的出库订单
     */
    void calcImpOrderLeftQty();

    ResultVo<String> updateCsTmpReturnCalcDataById(CsReturnCalcVo calcVo);

    ResultVo<String> deleteCsReturnApplyById(Integer id);

    /**
     * 根据入库数量和库存数量 反算impData表的出库数量
     */
    ResultVo<String> calCsImpExpQty();

    ResultVo<String> calcBalance(Date monthDate);

    ResultVo<List<CsBalanceCalcMasterDO>> getCalcbanceDate();

    CsStockSettingDO getCsStockSetting(String agentNo, String warehouseCode, String modelNo);

    void exportCalcRetrunData();

    void exportCsBalanceData(CsBalcenQryRequest request);

    /**
     * 获取客户的最大备库E金额
     * @param customerNo
     * @return
     */
    ResultVo<CsEAmountDTO> getCanUseEAmount(String customerNo);

    /**
     * 入库发票签收
     * @param dto
     * @return
     */
    ResultVo<String> recieveGoodsByDelivery(CsReceiveGoodsDTO dto);

    /**
     * 入库发票签收 -批量
     * @param dtoList
     * @return
     */
    ResultVo<String> recieveGoodsByDeliveryList(List<CsReceiveGoodsDTO> dtoList);

    ResultVo<String> syncCsImpDataToCost();

    ResultVo<List<CsImpdataVO>> listCsImpdata();

    ResultVo<String> updateImpDataRoId(List<CsImpdataVO> list);

    /**
     * 更改月结时间状态
     * @param id
     * @return
     */
    ResultVo<String> updateCsBalanceDateById(Integer id);

    /**
     * 更改月结计算时间
     * @param masterDO
     * @return
     */
    ResultVo<String> updateCsBalaceMothDate(CsBalanceCalcMasterDO masterDO);

    /**
     * 取消委托在库出库单
     * @param orderNos
     * @return
     */
    ResultVo<String> cancelCsExpDetailByOrderNo(List<String> orderNos);

    /**
     * 将退货单同步至
     * @param orderDO
     * @return
     */
    ResultVo<String> updateSalesInvoiceMidInfo(ReturnOrderDO orderDO);

    ResultVo<String> updateCsReturnApplyStatus(Integer id);

    void exportCsImpData(CsImportDetailRequestDTO requestDTO);

    void exportCsExpData(CsExportDetailRequestDTO requestDTO);

    ResultVo<String> updateTransOrderStatus(CsImpdataDO csImpdataDO);

    ResultVo<String> sendCsReturnApplyToEmail(Integer applyId);
}
