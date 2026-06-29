package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.inventory.*;
import com.smc.smccloud.model.product.CsStockStockTakeParam;
import org.apache.ibatis.annotations.Param;


import java.util.Date;
import java.util.List;

public interface InventoryService {

    /**
     * 根据型号查询仓库通用在库和专用在库
     *
     * @param modelNo 型号
     */
    ResultVo<List<WarehouseStockVO>> listWarehouseStock(String modelNo);

    /**
     * 根据库存属性列出数据
     * @param inventoryProperty
     * @return
     */
    ResultVo<PageInfo<InventoryVO>> listInventoryByProperty(OpsInventoryPropertyRequestDTO inventoryProperty);
    ResultVo<List<String>> listInventoryModelByProperty(OpsInventoryPropertyRequestDTO inventoryProperty);

    ResultVo<List<InventoryVO>> listSpecInventory(InventoryRequestDTO dto);

    ResultVo<List<OpsInventoryVO>> findInventoryListByModelNo(String modelNo);

    ResultVo<PageInfo<OpsInventoryPropertyVO>> listOpsInventoryProperty(OpsInventoryPropertyRequestDTO dto);
    /**
     * 获取在库数属性 ID
     *
     * @param dto
     * @return
     */
    ResultVo<List<Long>>getOpsInventoryPropertyId(OpsInventoryPropertyRequestDTO dto);
    /**
     * 获取在库表ID
     * @param inventoryProperty
     * @return
     */
    ResultVo<List<Long>> getInventoryIdByPropertyIds(OpsInventoryPropertyRequestDTO inventoryProperty);

    ResultVo<List<OpsInventoryVO>> getOpsInventoryByPropertyIds(OpsInventoryPropertyRequestDTO inventoryProperty) ;

    ResultVo<List<OpsInventoryVO>>  getInventoryMoveByProperty(OpsInventoryPropertyRequestDTO inventoryProperty);
    ResultVo<List<OpsInventoryVO>> getCanUseInventoryByProperty(OpsInventoryPropertyRequestDTO inventoryProperty);
    /**
     * 查询型号在库
     *
     * @param dto 型号在库条件 (必需: modelNo, warehouseCode, inventoryTypeCode)
     * @return 在库
     */
    ResultVo<Integer> getModelWarehouseStock(ModelWarehouseStockRequest dto);

    ResultVo<Integer> getInvByModel(String modelNo);

    /**
     * 查询库房属性
     */
    ResultVo<OpsInventoryPropertyVO> getOpsInventoryProperty(Long id);

    ResultVo<OpsInventoryPropertyVO> checkInventoryProperty(OpsInventoryPropertyVO vo);

//    ResultVo<OpsInventoryPropertyVO> checkAndCreateInventoryProperty(OpsInventoryPropertyVO vo);


    /**
     * 按库存属性汇总型号库存
     * 包括查在库可用(库存表),订货中(采购表),bin数据(binQty,月用量)
     *
     * @param dto
     * @return
     */
    ResultVo<List<InventorySummaryVO>> listInventorySummaryByPropertyId(InventoryRequestDTO dto);

    /**
     * 更新CN各工厂库存
     *
     * @return
     */
    ResultVo<String> autoUpdateStock();

    /**
     * 查询型号在仓库的通用在库可用数量和在途数量
     *
     * @param dto 必填: modelNos && warehouseCode
     * @return 型号在仓库的通用在库可用数量和在途数量
     */
    ResultVo<List<ModelWarehouseStockVO>> listModelWarehouseStock(ModelWarehouseStockRequest dto);

    /**
     * 查询客户专备在库
     *
     * @param dto 必填: modelNos
     * @return 库存信息
     */
    ResultVo<List<SpecStockVO>> listCustomerSpecStock(ModelWarehouseStockRequest dto);

    /**
     * 根据条件查询可用库存
     *
     * @param dto ModelWarehouseStockRequest
     * @return 库存信息
     */
    ResultVo<List<InventoryVO>> listCanUseInventory(ModelWarehouseStockRequest dto);


    /**
     * 查询物流中心可用库存
     * @param modelNos
     * @return
     */
    ResultVo<List<WarehouseInventoryVO>> getLogisticWarehouseCanUseStock(List<String> modelNos);

    /**
     * 根据仓库与型号查询可用库存
     */
    ResultVo<List<WarehouseInventoryVO>> getWarehouseCanUseStock(List<String> warehouseCodes, List<String> modelNos);


    /**
     * 根据库房代码和型号查询在库数量
     */
    ResultVo<List<OpsInventoryVO>> findInventQtyByModelNo(String warehouseCode,String modelNo);


    /**
     * 盘点-查询待盘点明细
     */
    ResultVo<PageInfo<InventoryDetailDTO>> listCsStockStockTake(CsStockStockTakeParam param);


    ResultVo<List<InventoryForShikomiApplyVO>> listInvnetoryForShikomi(String[] modelNos);

    ResultVo<List<CustomerModelStockVO>> listCustomerBinModelInventory(InventoryRequestDTO dto);

    /**
     * 包含了先行在库的属性查询
     * @param dto
     * @return
     */
    ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLogData(InventoryLogRequstDTO dto);

    /**
     * 只有记录的数制
     * @param dto
     * @return
     */
    ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLog(InventoryLogRequstDTO dto);

    void exportInventoryLogData(InventoryLogRequstDTO dto);

    /**
     * 定时保存无库存bin型号
     */
    void updateZeroInventory();

    void updateZeroInventoryVTest();

    ResultVo<PageInfo<ZeroInventoryVO>> listZeroInventoryData(ZeroInventoryDTO dto);

    void exportZeroInventoryData(ZeroInventoryDTO dto);

    ResultVo<List<OpsInventoryVO>> findInventoryByWareHouseCode(String wareHouseCode);

    ResultVo<Integer> getCanUseQty(String modelNo);

     ResultVo<Integer> getCustomerCanUseQty(InventoryRequestDTO dto);

     ResultVo<String> syncZeroInventory(Date calcDate);

    ResultVo<String> syncZeroInventoryVTest(Date calcDate);

    ResultVo<String> calcOPSInventoryForManu();

    /**
     * 库存查询
     *提供给门户手机端
     */
    ResultVo<List<SMSInventoryVO>> getModelInventoryByWarehouse(String modelNo);

    /**
     * 统计专备在库查询
     * @param request
     * @return
     */
    ResultVo<PageInfo<SpecStatisticsVO>> findUserStockStatisList(SpecStatisticsRequest request,int pageNumber,int pageSize);

    ResultVo<List<OpsInventoryVO>> getInventoryMoveByModels(  OpsInventoryPropertyRequestDTO inventoryProperty);

    ///**
    // * 根据InventoryId交易出库数量= JYCK客户交易出库单-THRK退货入库收货
    // * @param inventoryProperty
    //
    // * @return
    // */
    //ResultVo<List<ModelOrderExpFreqVO>> getSalesQuantityByInventoryId(OpsInventoryPropertyRequestDTO inventoryProperty);

    ///**
    // * 根据型号交易出库数量= JYCK客户交易出库单-THRK退货入库收货
    // * @param inventoryProperty
    // * @return
    // */
    //ResultVo<List<ModelOrderExpFreqVO>>  getSalesQuantityByModelNo(OpsInventoryPropertyRequestDTO inventoryProperty);

    /**
     *
     * @param inventoryProperty  仓库属性

     * @return
     */
    ResultVo<List<ModelOrderExpFreqVO>> getSalesQuantityAndFreq(OpsInventoryPropertyRequestDTO inventoryProperty  );

    /**
     * 根据InventoryId,orderNo,ItemNO统计入数量和最近入库时间
     * @param dto
     * @return
     */
    //ResultVo<OpsInventoryLogVO> getInventoryLogImpQtyByOrder(InventoryLogRequstDTO dto);

    /**
     * 根据InventoryId,分页查询入库数据
     * @param dto
     * @return
     */
    //ResultVo<List<OpsInventoryLogVO>> listInventoryLogByInventoryIdForPage(InventoryLogRequstDTO dto);

    //ResultVo< List<OpsInventoryLog>> getImpQtyByInvetoryId( OpsInventoryLogRequestDTO dto);
}

