package com.sales.ops.web.controller.warehouse;

import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.sales.ops.db.entity.OpsWarehouseSupplierConfig;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.warehouse.OPSWarehouseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops 仓库相关查询缓存操作
 * @date ：Created in 2021/10/19 8:07
 */
@CrossOrigin
@RestController
@RequestMapping("/ops/warehouse/s")
public class OPSWarehouseController {

    @Autowired
    private OPSWarehouseService opsWarehouseService;

    /**
     * 查仓库
     * @param warehouseCode
     * @return
     * testTime 2021/10/19 12:05 C14717
     */
    @RequestMapping("/warehouse")
    public CommonResult<OpsWarehouse> searchWarehouse(@RequestParam String warehouseCode){
        try {
            OpsWarehouse result = opsWarehouseService.selectWareHouseByCondition(warehouseCode);
            return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 刷新 仓库
     * @param mi 分钟
     * @return
     */
    @RequestMapping("/warehouse/refresh")
    public CommonResult<List<String>> refreshWarehouseData(@RequestParam String mi){
        try {
            List<String> list = opsWarehouseService.refreshWareHouseData(mi);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 查仓库供应商关系表 OpsWarehouseSupplierConfig
     * @param warehouseCode
     * @return
     */
    @RequestMapping("/warehouseSupplierConfig")
    public CommonResult<List<OpsWarehouseSupplierConfig>> searchWarehouseSupplierConfig(@RequestParam String warehouseCode){
        try {
            List<OpsWarehouseSupplierConfig> list = opsWarehouseService.selectWarehouseSupplierConfigByCondition(warehouseCode);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 刷新 仓库供应商关系表 OpsWarehouseSupplierConfig
     * @param mi 分钟
     * @return
     */
    @RequestMapping("/warehouseSupplierConfig/refresh")
    public CommonResult<List<String>> refreshWarehouseSupplierConfigData(@RequestParam String mi){
        try {
            List<String> list =  opsWarehouseService.refreshWarehouseSupplierConfigData(mi);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 查询营业所仓库关系表 warehouseSalesbranchConfig
     * @param warehouseCode
     * @return
     */
    @RequestMapping("/warehouseSalesbranchConfig")
    public CommonResult<List<OpsWarehouseSalesbranchConfig>> searchWarehouseSalesbranchConfig(@RequestParam String warehouseCode){
        try {
            List<OpsWarehouseSalesbranchConfig> list = opsWarehouseService.selectOpsWarehouseSalesbranchConfig(warehouseCode);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 刷新 营业所仓库关系表 warehouseSalesbranchConfig
     * @param mi 分钟
     * @return
     */
    @RequestMapping("/warehouseSalesbranchConfig/refresh")
    public CommonResult<List<String>> refreshWarehouseSalesbranchConfigData(@RequestParam String mi){
        try {
            List<String> list = opsWarehouseService.refreshWarehouseSalesbranchConfigData(mi);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }
}
