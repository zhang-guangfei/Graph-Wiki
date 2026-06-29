package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.inventory.*;
import com.smc.smccloud.model.product.CsStockStockTakeParam;
import com.smc.smccloud.service.InventoryServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Component
public class InventoryServiceFeignHystrix implements FallbackFactory<InventoryServiceFeignApi> {

    @Override
    public InventoryServiceFeignApi create(Throwable cause) {
        return new InventoryServiceFeignApi() {
            @Override
            public ResultVo<List<WarehouseStockVO>> listWarehouseStock(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<PageInfo<InventoryVO>> listInventoryByProperty(OpsInventoryPropertyRequestDTO inventoryProperty){

                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<List<String>> listInventoryModelByProperty(OpsInventoryPropertyRequestDTO inventoryProperty){

                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<List<InventoryVO>> listSpecInventory(InventoryRequestDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<PageInfo<OpsInventoryPropertyVO>> listOpsInventoryProperty(OpsInventoryPropertyRequestDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<Integer> getModelWarehouseStock(ModelWarehouseStockRequest dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<Integer> getInvByModel( String modelNo){
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<OpsInventoryPropertyVO> getOpsInventoryProperty(Long id) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<Long>> getOpsInventoryPropertyId(OpsInventoryPropertyRequestDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<Long>> getInventoryIdByPropertyIds(OpsInventoryPropertyRequestDTO inventoryProperty) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<OpsInventoryVO>> getOpsInventoryByPropertyIds(OpsInventoryPropertyRequestDTO inventoryProperty) {
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<List<OpsInventoryVO>> getCanUseInventoryByProperty(@RequestBody OpsInventoryPropertyRequestDTO inventoryProperty){
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<OpsInventoryPropertyVO> checkInventoryProperty(OpsInventoryPropertyVO vo) {
                return ResultVo.failure("请求失败,服务降级");
            }

//            @Override
//            public ResultVo<OpsInventoryPropertyVO> checkAndCreateInventoryProperty(OpsInventoryPropertyVO vo) {
//                return ResultVo.failure("请求失败,服务降级");
//            }

            @Override
            public ResultVo<List<InventorySummaryVO>> listInventorySummaryByPropertyId(InventoryRequestDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> autoUpdateStock() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> importJPSTockData(@Valid List<InventorySupplierVO> list) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> parseJPSTockFile(MultipartFile file) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<InventoryVO>> listCanUseInventory(ModelWarehouseStockRequest dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<SpecStockVO>> listCustomerSpecStock(ModelWarehouseStockRequest dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<WarehouseInventoryVO>> getLogisticWarehouseCanUseStock(InventoryRequestDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<WarehouseInventoryVO>> getWarehouseCanUseStock(InventoryRequestDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<OpsInventoryVO>> findInventQtyByModelNo(String warehouseCode, String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<PageInfo<InventoryDetailDTO>> listCsStockStockTake(CsStockStockTakeParam param) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<CustomerModelStockVO>> listCustomerBinModelInventory(InventoryRequestDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<InventoryForShikomiApplyVO>> listInvnetoryForShikomi(String[] modelNos) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLogData(InventoryLogRequstDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<PageInfo<OpsInventoryLogVO>> listInventoryLog(InventoryLogRequstDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<PageInfo<ZeroInventoryVO>> listZeroInventoryData(ZeroInventoryDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<OpsInventoryVO>> findInventoryListByModelNo(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<OpsInventoryVO>> findInventoryByWareHouseCode(String wareHouseCode) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<Integer> getCanUseQty(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<Integer> getCustomerCanUseQty(InventoryRequestDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public void exportZeroInventoryData(ZeroInventoryDTO dto) {
                return;
            }

            @Override
            public ResultVo<String> calcOPSInventoryForManu() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public void exportInventoryLogData(InventoryLogRequstDTO dto) {
                return;
            }

            @Override
            public ResultVo<String> paseJPInventorySupplier() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> impGPInventory() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public void updateZeroInventory() {
                return;
            }

            @Override
            public ResultVo<List<OpsInventoryVO>> getInventoryMoveByModels( OpsInventoryPropertyRequestDTO inventoryProperty) {
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<List<OpsInventoryVO>> getInventoryMoveByProperty( OpsInventoryPropertyRequestDTO inventoryProperty) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<ModelOrderExpFreqVO>> getSalesQuantityAndFreq(OpsInventoryPropertyRequestDTO inventoryProperty) {
                return ResultVo.failure("请求失败,服务降级");
            }
        };
    }

}
