package com.smc.smccloud.web.rpc;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.inventory.*;
import com.smc.smccloud.model.product.CsStockStockTakeParam;
import com.smc.smccloud.service.InventoryService;
import com.smc.smccloud.service.InventoryServiceFeignApi;
import com.smc.smccloud.service.InventorySupplierService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class InventoryServiceFeignClient implements InventoryServiceFeignApi {

    @Resource
    private InventoryService inventoryService;

    @Resource
    private InventorySupplierService inventorySupplierService;


    @Override
    public ResultVo<List<WarehouseStockVO>> listWarehouseStock(String modelNo) {
        return inventoryService.listWarehouseStock(modelNo);
    }
    @Override
    public ResultVo<PageInfo<InventoryVO>> listInventoryByProperty(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty){
        return inventoryService.listInventoryByProperty(inventoryProperty);
    }
    @Override
    public ResultVo<List<String>> listInventoryModelByProperty(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty){
        return inventoryService.listInventoryModelByProperty(inventoryProperty);
    }

    @Override
    public ResultVo<List<InventoryVO>> listSpecInventory(InventoryRequestDTO dto) {
        return inventoryService.listSpecInventory(dto);
    }

    @Override
    public ResultVo<PageInfo<OpsInventoryPropertyVO>> listOpsInventoryProperty(OpsInventoryPropertyRequestDTO dto) {
        return inventoryService.listOpsInventoryProperty(dto);
    }
    @Override
    public ResultVo<List<Long>>   getOpsInventoryPropertyId( OpsInventoryPropertyRequestDTO dto){
        return inventoryService.getOpsInventoryPropertyId(dto);
    }
    @Override
    public ResultVo<List<Long>> getInventoryIdByPropertyIds(OpsInventoryPropertyRequestDTO inventoryProperty){
        return inventoryService.getInventoryIdByPropertyIds(inventoryProperty);
    }
    @Override
    public ResultVo<List<OpsInventoryVO>>   getOpsInventoryByPropertyIds( OpsInventoryPropertyRequestDTO inventoryProperty){
        return inventoryService.getOpsInventoryByPropertyIds(inventoryProperty);
    }

    @Override
    public ResultVo<List<OpsInventoryVO>>   getCanUseInventoryByProperty( OpsInventoryPropertyRequestDTO inventoryProperty){
        return inventoryService.getCanUseInventoryByProperty(inventoryProperty);
    }


//    /**
//     * 库存在途数查询
//     * @param modelNo
//     * @return
//     */
//    @Override
//    public ResultVo<Integer> sumInventoryTransQty(String modelNo) {
//        return inventoryService.sumInventoryTransQty(modelNo);
//    }

    @Override
    public ResultVo<Integer> getModelWarehouseStock(ModelWarehouseStockRequest dto) {
        return inventoryService.getModelWarehouseStock(dto);
    }

    @Override
    public ResultVo<Integer> getInvByModel(String modelNo){
        return inventoryService.getInvByModel(modelNo);
    }

    @Override
    public ResultVo<OpsInventoryPropertyVO> getOpsInventoryProperty(Long id) {
        return inventoryService.getOpsInventoryProperty(id);
    }

    @Override
    public ResultVo<OpsInventoryPropertyVO> checkInventoryProperty(OpsInventoryPropertyVO vo) {
        return inventoryService.checkInventoryProperty(vo);
    }

    // Edit by DengDengHui, 2022-10-20 for bug-8370
//    @Override
//    public ResultVo<OpsInventoryPropertyVO> checkAndCreateInventoryProperty(OpsInventoryPropertyVO vo) {
//        return inventoryService.checkAndCreateInventoryProperty(vo);
//    }

    /**
     * 按库存属性汇总型号库存
     * 包括查在库可用(库存表),订货中(采购表),bin数据(binQty,月用量)
     *
     * @param dto
     * @return
     */
    @Override
    public ResultVo<List<InventorySummaryVO>> listInventorySummaryByPropertyId(InventoryRequestDTO dto) {
        return inventoryService.listInventorySummaryByPropertyId(dto);
    }

    @Override
    public ResultVo<String> autoUpdateStock() {
        return inventoryService.autoUpdateStock();
    }

    @Override
    public ResultVo<String> importJPSTockData(@Valid List<InventorySupplierVO> list) {
        return inventorySupplierService.importJPStockData(list);
    }

    @Override
    public ResultVo<String> parseJPSTockFile(MultipartFile file) {
        return inventorySupplierService.parseJPSTockFile(file);
    }

    @Override
    public ResultVo<List<InventoryVO>> listCanUseInventory(ModelWarehouseStockRequest dto) {
        return inventoryService.listCanUseInventory(dto);
    }

    @Override
    public ResultVo<List<SpecStockVO>> listCustomerSpecStock(@RequestBody ModelWarehouseStockRequest dto) {
        return inventoryService.listCustomerSpecStock(dto);
    }

    @Override
    public ResultVo<List<WarehouseInventoryVO>> getLogisticWarehouseCanUseStock(InventoryRequestDTO dto) {
        if (CollectionUtils.isEmpty(dto.getModelNos())) {
            return ResultVo.failure("请传入型号数组");
        }
        return inventoryService.getLogisticWarehouseCanUseStock(dto.getModelNos());
    }

    @Override
    public ResultVo<List<WarehouseInventoryVO>> getWarehouseCanUseStock(InventoryRequestDTO dto) {
        if (CollectionUtils.isEmpty(dto.getWarehouseCodes()) || CollectionUtils.isEmpty(dto.getModelNos())) {
            return ResultVo.failure("请传入仓库和型号数组");
        }
        return inventoryService.getWarehouseCanUseStock(dto.getWarehouseCodes(), dto.getModelNos());
    }

    @Override
    public ResultVo<List<OpsInventoryVO>> findInventQtyByModelNo(String warehouseCode, String modelNo) {
        return inventoryService.findInventQtyByModelNo(warehouseCode, modelNo);
    }

    @Override
    public ResultVo<PageInfo<InventoryDetailDTO>> listCsStockStockTake(CsStockStockTakeParam param) {
        return inventoryService.listCsStockStockTake(param);
    }

    @Override
    public ResultVo<List<CustomerModelStockVO>> listCustomerBinModelInventory(InventoryRequestDTO dto) {
        return inventoryService.listCustomerBinModelInventory(dto);
    }

    @Override
    public ResultVo<List<InventoryForShikomiApplyVO>> listInvnetoryForShikomi(String[] modelNos) {
        return inventoryService.listInvnetoryForShikomi(modelNos);
    }

    @Override
    public ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLogData(InventoryLogRequstDTO dto) {
        return inventoryService.listInventoryLogData(dto);
    }
    @Override
    public ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLog(InventoryLogRequstDTO dto) {
        return inventoryService.listInventoryLog(dto);
    }
    @Override
    public void updateZeroInventory() {
        inventoryService.updateZeroInventory();
    }

    @Override
    public ResultVo<PageInfo<ZeroInventoryVO>> listZeroInventoryData(ZeroInventoryDTO dto) {
        return inventoryService.listZeroInventoryData(dto);
    }

    @Override
    public ResultVo<List<OpsInventoryVO>> findInventoryListByModelNo(String modelNo) {
        return inventoryService.findInventoryListByModelNo(modelNo);
    }

    @Override
    public ResultVo<List<OpsInventoryVO>> findInventoryByWareHouseCode(String wareHouseCode) {
        return inventoryService.findInventoryByWareHouseCode(wareHouseCode);
    }

    @Override
    public ResultVo<Integer> getCanUseQty(String modelNo) {
        return inventoryService.getCanUseQty(modelNo);
    }

    @Override
    public ResultVo<Integer> getCustomerCanUseQty(InventoryRequestDTO dto) {
        return inventoryService.getCustomerCanUseQty(dto);
    }

    @Override
    public void exportZeroInventoryData(ZeroInventoryDTO dto) {
        inventoryService.exportZeroInventoryData(dto);
    }

    @Override
    public ResultVo<String> calcOPSInventoryForManu() {
        return inventoryService.calcOPSInventoryForManu();
    }

    @Override
    public void exportInventoryLogData(InventoryLogRequstDTO dto) {
        inventoryService.exportInventoryLogData(dto);
    }

    @Override
    public ResultVo<String> paseJPInventorySupplier() {
        return inventorySupplierService.paseJPInventorySupplier();
    }

    @Override
    public ResultVo<String> impGPInventory() {
        return inventorySupplierService.impGPInventory();
    }

    @Override
    public ResultVo<List<OpsInventoryVO>> getInventoryMoveByModels(OpsInventoryPropertyRequestDTO inventoryProperty) {
        return inventoryService.getInventoryMoveByModels(inventoryProperty);
    }
    @Override
    public ResultVo<List<OpsInventoryVO>> getInventoryMoveByProperty(OpsInventoryPropertyRequestDTO inventoryProperty) {
        return inventoryService.getInventoryMoveByProperty(inventoryProperty);
    }
    //@Override
    //public  ResultVo<List<ModelOrderExpFreqVO>>  getSalesQuantityByInventoryId(OpsInventoryPropertyRequestDTO inventoryProperty){
    //    return inventoryService.getSalesQuantityByInventoryId(inventoryProperty);
    //}
    //@Override
    //public  ResultVo<List<ModelOrderExpFreqVO>>  getSalesQuantityByModelNo(OpsInventoryPropertyRequestDTO inventoryProperty){
    //    return inventoryService.getSalesQuantityByModelNo(inventoryProperty);
    //}
    @Override
    public ResultVo<List<ModelOrderExpFreqVO>> getSalesQuantityAndFreq(OpsInventoryPropertyRequestDTO inventoryProperty ){
        return inventoryService.getSalesQuantityAndFreq(inventoryProperty);
    }
    //@Override
    //public ResultVo<OpsInventoryLogVO> getInventoryLogImpQtyByOrder(InventoryLogRequstDTO dto) {
    //    return inventoryService.getInventoryLogImpQtyByOrder(dto);
    //}

    //@Override
    //public ResultVo<List<OpsInventoryLogVO>> listInventoryLogByInventoryIdForPage(InventoryLogRequstDTO dto){
    //    return inventoryService.listInventoryLogByInventoryIdForPage(dto);
    //}

    //@Override
    //public ResultVo<List<OpsInventoryLog>> getImpQtyByInvetoryId(OpsInventoryLogRequestDTO dto){
    //    return inventoryService.getImpQtyByInvetoryId(dto);
    //}
}
