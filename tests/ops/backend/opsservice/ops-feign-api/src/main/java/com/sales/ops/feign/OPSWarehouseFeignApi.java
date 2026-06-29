package com.sales.ops.feign;

import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.sales.ops.db.entity.OpsWarehouseSupplierConfig;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：仓库相关基本数据缓存接口信息
 * @date ：Created in 2021/10/19 8:19
 */
@FeignClient(name = "ba-service",contextId="warehouse")
public interface OPSWarehouseFeignApi {


    /**
     * 查仓库（数据库或缓存）
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/ops/warehouse/s/warehouse",method = RequestMethod.POST)
     CommonResult<OpsWarehouse> searchWarehouse(@RequestParam("warehouseCode") String warehouseCode);

    /**
     * 刷新仓库（缓存）
     * @param mi 分钟
     * @return
     */
    @RequestMapping(value = "/ops/warehouse/s/warehouse/refresh",method = RequestMethod.GET)
    CommonResult<List<String>> refreshWarehouse(@RequestParam("mi") String mi);

    /**
     * 查仓库供应商关系表（数据库或缓存）
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/ops/warehouse/s/warehouseSupplierConfig",method = RequestMethod.POST)
    CommonResult<List<OpsWarehouseSupplierConfig>> searchWarehouseSupplierConfig(@RequestParam("warehouseCode") String warehouseCode);

    /**
     * 刷新供应商关系表（缓存）
     * @param mi 分钟
     * @return
     */
    @RequestMapping(value = "/ops/warehouse/s/warehouseSupplierConfig/refresh",method = RequestMethod.GET)
    CommonResult<List<String>> refreshWarehouseSupplierConfig(@RequestParam("mi") String mi);

    /**
     * 查仓库营业所关系表（数据库或缓存）
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/ops/warehouse/s/warehouseSalesbranchConfig",method = RequestMethod.POST)
    CommonResult<List<OpsWarehouseSalesbranchConfig>> searchWarehouseSalesbranchConfig(@RequestParam("warehouseCode") String warehouseCode);

    /**
     * 刷新仓库营业所关系表（缓存）
     * @param mi 分钟
     * @return
     */
    @RequestMapping(value = "/ops/warehouse/s/warehouseSalesbranchConfig/refresh",method = RequestMethod.GET)
    CommonResult<List<String>> refreshWarehouseSalesbranchConfig(@RequestParam("mi") String mi);



}
