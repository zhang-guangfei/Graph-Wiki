package com.smc.smccloud.service;


import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.inventory.*;
import com.smc.smccloud.model.product.CsStockStockTakeParam;
import com.smc.smccloud.service.hystrix.InventoryServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

// url = "http://10.116.194.236:8101"

@FeignClient(name = "product-service",
        contextId = "product-inventory",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = InventoryServiceFeignHystrix.class)
public interface InventoryServiceFeignApi {

    /**
     * 查询仓库的（通用和专备）库存汇总
     *
     * @param modelNo
     * @return
     */
    @RequestMapping(value = "/product/inventory/getWarehouseStock", method = RequestMethod.POST)
    ResultVo<List<WarehouseStockVO>> listWarehouseStock(@RequestParam("modelNo") String modelNo);

    @RequestMapping(value = "/product/inventory/listInventoryByProperty", method = RequestMethod.POST)
    ResultVo<PageInfo<InventoryVO>> listInventoryByProperty(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);

    @RequestMapping(value = "/product/inventory/listInventoryModelByProperty", method = RequestMethod.POST)
    ResultVo<List<String>> listInventoryModelByProperty(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);

    /**
     * 查询专用的在库
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/product/inventory/listSpecInventory", method = RequestMethod.POST)
    ResultVo<List<InventoryVO>> listSpecInventory(@RequestBody InventoryRequestDTO dto);

    /**
     * 查询库存属性
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/product/inventory/listOpsInventoryProperty", method = RequestMethod.POST)
    ResultVo<PageInfo<OpsInventoryPropertyVO>> listOpsInventoryProperty(@RequestBody OpsInventoryPropertyRequestDTO dto);

    /**
     * 查询库存属性ID
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/product/inventory/getOpsInventoryPropertyId", method = RequestMethod.POST)
    ResultVo<List<Long>> getOpsInventoryPropertyId(@RequestBody OpsInventoryPropertyRequestDTO dto);

    /**
     * 查询在库数ID
     *
     * @param inventoryProperty
     * @return
     */
    @RequestMapping(value = "/product/inventory/getInventoryIdByPropertyIds", method = RequestMethod.POST)
    ResultVo<List<Long>> getInventoryIdByPropertyIds(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);

    /**
     * 根据库存属性ID，查询在库数
     *
     * @param inventoryProperty
     * @return
     */
    @RequestMapping(value = "/product/inventory/getOpsInventoryByPropertyIds", method = RequestMethod.POST)
    ResultVo<List<OpsInventoryVO>> getOpsInventoryByPropertyIds(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);

    /**
     * 根据库存属性，查询在库数
     * @param inventoryProperty
     * @return
     */
    @RequestMapping(value = "/product/inventory/getCanUseInventoryByProperty", method = RequestMethod.POST)
    ResultVo<List<OpsInventoryVO>> getCanUseInventoryByProperty(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);

    /**
     * 型号在途数
     * @param modelNo
     * @return
     */
//    @RequestMapping(value = "/product/inventory/sumInventoryTransQty", method = RequestMethod.GET)
//    ResultVo<Integer> sumInventoryTransQty(@RequestParam("modelNo") String modelNo);


    /**
     * 查询型号在库
     *
     * @param dto 型号在库条件 (必需: modelNo, warehouseCode, inventoryTypeCode)
     * @return 在库
     */
    @RequestMapping(value = "/product/inventory/getModelWarehouseStock", method = RequestMethod.POST)
    ResultVo<Integer> getModelWarehouseStock(@RequestBody ModelWarehouseStockRequest dto);

    @RequestMapping(value = "/product/inventory/getInvByModel", method = RequestMethod.POST)
    ResultVo<Integer> getInvByModel( @RequestParam("modelNo") String modelNo);

    /**
     * 查询库房属性
     */
    @RequestMapping(value = "/product/inventory/getOpsInventoryProperty", method = RequestMethod.GET)
    ResultVo<OpsInventoryPropertyVO> getOpsInventoryProperty(@RequestParam("id") Long id);

    /**
     * 检查 InventoryProperty
     */
    @RequestMapping(value = "/product/inventory/checkInventoryProperty", method = RequestMethod.POST)
    ResultVo<OpsInventoryPropertyVO> checkInventoryProperty(@RequestBody OpsInventoryPropertyVO vo);

    // Edit by DengDengHui, 2022-10-20 for bug-8370
//    /**
//     * 检查和创建库存属性id
//     */
//    @RequestMapping(value = "/product/inventory/checkAndCreateInventoryProperty", method = RequestMethod.POST)
//    ResultVo<OpsInventoryPropertyVO> checkAndCreateInventoryProperty(@RequestBody OpsInventoryPropertyVO vo);

    /**
     * 按库存属性汇总型号库存
     * 包括查在库可用(库存表),订货中(采购表),bin数据(binQty,月用量)
     */
    @RequestMapping(value = "/product/inventory/listInventorySummaryByPropertyId", method = RequestMethod.POST)
    ResultVo<List<InventorySummaryVO>> listInventorySummaryByPropertyId(@RequestBody InventoryRequestDTO dto);


    /**
     * 更新CN各工厂库存
     *
     * @return string
     */
    @RequestMapping(value = "/product/inventory/autoUpdateStock", method = RequestMethod.GET)
    ResultVo<String> autoUpdateStock();

    /**
     * 导入库存供应商
     */
    @RequestMapping(value = "/order/import/importJPSTockData", method = RequestMethod.POST)
    ResultVo<String> importJPSTockData(@Valid @RequestBody List<InventorySupplierVO> list);

    /**
     * 解析库存供应商文件JPSTOCK.DAT
     */
    @PostMapping(value = "/order/import/parseJPSTockFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVo<String> parseJPSTockFile(@RequestPart("file") MultipartFile file);

    /**
     * 查询型号的通用库存和客户专备
     *
     * @param dto ModelWarehouseStockRequest
     * @return list
     */
    @RequestMapping(value = "/order/inventory/listCanUseInventory", method = RequestMethod.POST)
    ResultVo<List<InventoryVO>> listCanUseInventory(@RequestBody ModelWarehouseStockRequest dto);

    /**
     * 查询客户专备在库情况
     *
     * @param dto modelNos
     * @return 客户专备情况
     */
    @RequestMapping(value = "/product/inventory/listCustomerSpecStock", method = RequestMethod.POST)
    ResultVo<List<SpecStockVO>> listCustomerSpecStock(@RequestBody ModelWarehouseStockRequest dto);

    /**
     * 查询物流中心可用库存
     */
    @RequestMapping(value = "/product/inventory/getLogisticWarehouseCanUseStock", method = RequestMethod.POST)
    ResultVo<List<WarehouseInventoryVO>> getLogisticWarehouseCanUseStock(@RequestBody InventoryRequestDTO dto);

    /**
     * 根据仓库型号数组查询可用库存
     */
    @RequestMapping(value = "/product/inventory/getWarehouseCanUseStock", method = RequestMethod.POST)
    ResultVo<List<WarehouseInventoryVO>> getWarehouseCanUseStock(@RequestBody InventoryRequestDTO dto);

    /**
     * 根据库房代码和型号查询在库数量
     */
    @RequestMapping(value = "/product/inventory/findInventQtyByModelNofindInventQtyByModelNo", method = RequestMethod.GET)
    ResultVo<List<OpsInventoryVO>> findInventQtyByModelNo(@RequestParam("warehouseCode") String warehouseCode, @RequestParam("modelNo") String modelNo);


    /**
     * 盘点-查询待盘点明细
     *
     * @return
     */
    @RequestMapping(value = "/product/inventory/listCsStockStockTake", method = RequestMethod.POST)
    ResultVo<PageInfo<InventoryDetailDTO>> listCsStockStockTake(@RequestBody CsStockStockTakeParam param);

    /**
     * 根据客户代码、型号列表查询库存信息
     */
    @RequestMapping(value = "/product/inventory/listCustomerModelCanUseInventory", method = RequestMethod.POST)
    ResultVo<List<CustomerModelStockVO>> listCustomerBinModelInventory(@RequestBody InventoryRequestDTO dto);

    @RequestMapping(value = "/product/inventory/listInvnetoryForShikomi", method = RequestMethod.POST)
    ResultVo<List<InventoryForShikomiApplyVO>> listInvnetoryForShikomi(@RequestBody String[] modelNos);

    @RequestMapping(value = "/product/inventory/listInventoryLogData", method = RequestMethod.POST)
    ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLogData(@RequestBody InventoryLogRequstDTO dto);

    @RequestMapping(value = "/product/inventory/listInventoryLog", method = RequestMethod.POST)
    ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLog(@RequestBody InventoryLogRequstDTO dto);

    @RequestMapping(value = "/product/inventory/updateZeroInventory", method = RequestMethod.POST)
    void updateZeroInventory();

    @RequestMapping(value = "/product/inventory/listZeroInventoryData", method = RequestMethod.POST)
    ResultVo<PageInfo<ZeroInventoryVO>> listZeroInventoryData(@RequestBody ZeroInventoryDTO dto);

    @RequestMapping(value = "/product/inventory/findInventoryListByModelNo", method = RequestMethod.GET)
    ResultVo<List<OpsInventoryVO>> findInventoryListByModelNo(@RequestParam("modelNo") String modelNo);

    @RequestMapping(value = "/product/inventory/findInventoryByWareHouseCode", method = RequestMethod.GET)
    ResultVo<List<OpsInventoryVO>> findInventoryByWareHouseCode(@RequestParam("wareHouseCode") String wareHouseCode);

    @RequestMapping(value = "/product/inventory/getCanUseQty", method = RequestMethod.GET)
    ResultVo<Integer> getCanUseQty(@RequestParam("modelNo") String modelNo);

    // 返回通用和客户专用库存之和 (仅中心仓)
    @PostMapping("/product/getCustomerCanUseQty")
    ResultVo<Integer> getCustomerCanUseQty(@RequestBody InventoryRequestDTO dto);

    @RequestMapping(value = "/product/inventory/exportZeroInventoryData", method = RequestMethod.POST)
    void exportZeroInventoryData(@RequestBody ZeroInventoryDTO dto);

    @RequestMapping(value = "/product/inventory/calcOPSInventoryForManu", method = RequestMethod.GET)
    ResultVo<String> calcOPSInventoryForManu();

    @RequestMapping(value = "/product/inventory/exportInventoryLogData", method = RequestMethod.POST)
    void exportInventoryLogData(@RequestBody InventoryLogRequstDTO dto);

    /**
     * 解析http://192.168.168.4:9999/JP-CN/ 日本供应商库存
     */
    @RequestMapping(value = "/product/inventory/paseJPInventorySupplier", method = RequestMethod.GET)
    ResultVo<String> paseJPInventorySupplier();

    /**
     * 导入更新广州制造库存
     */
    @RequestMapping(value = "/product/inventory/impGPInventory", method = RequestMethod.GET)
    ResultVo<String> impGPInventory();

    /**
     * 在途数
     *
     * @param inventoryProperty
     * @return
     */
    @RequestMapping(value = "/product/inventory/getInventoryMoveByModels", method = RequestMethod.GET)
    ResultVo<List<OpsInventoryVO>> getInventoryMoveByModels(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);

    @RequestMapping(value = "/product/inventory/getInventoryMoveByProperty", method = RequestMethod.POST)
    ResultVo<List<OpsInventoryVO>> getInventoryMoveByProperty(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);
    /**
     * 交易出库数量= JYCK客户交易出库单-THRK退货入库收货
     *
     * @param inventoryProperty
     * @return
     */
    //@RequestMapping(value = "/product/inventory/getSalesQuantityByInventoryId", method = RequestMethod.POST)
    //ResultVo<List<ModelOrderExpFreqVO>> getSalesQuantityByInventoryId(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);

    //@RequestMapping(value = "/product/inventory/getSalesQuantityByModelNo", method = RequestMethod.POST)
    //ResultVo<List<ModelOrderExpFreqVO>> getSalesQuantityByModelNo(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);

    @RequestMapping(value = "/product/inventory/getSalesQuantityAndFreq", method = RequestMethod.POST)
    ResultVo<List<ModelOrderExpFreqVO>> getSalesQuantityAndFreq(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty);

    //@RequestMapping(value = "/product/inventory/getInventoryLogImpQtyByOrder", method = RequestMethod.POST)
    //ResultVo<OpsInventoryLogVO> getInventoryLogImpQtyByOrder(@RequestBody InventoryLogRequstDTO dto);

    //@RequestMapping(value = "/product/inventory/listInventoryLogByInventoryIdForPage", method = RequestMethod.POST)
    //ResultVo<List<OpsInventoryLogVO>> listInventoryLogByInventoryIdForPage(@RequestBody InventoryLogRequstDTO dto);

    //@RequestMapping(value = "/product/inventory/getImpQtyByInvetoryId", method = RequestMethod.POST)
    //ResultVo<List<OpsInventoryLog>> getImpQtyByInvetoryId(@RequestBody OpsInventoryLogRequestDTO dto);
}
