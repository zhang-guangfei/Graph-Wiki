package com.sales.ops.feign.kettlejob;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lyc
 * @Date 2025/6/30 9:40
 * @Descripton TODO
 */
@FeignClient(name = "kettle-service", contextId = "kettleService")
public interface DDSKettleJobFeignApi {


    /**
     * 同步ops_invntory_dds增量kettle同步任务
     */
    @GetMapping("/kettle/opsInventoryDdsIncrementTrans")
    ResultVo<String> opsInventoryDdsIncrementTrans();


    /**
     * 全量同步ops_inventory_type_dds
     */
    @GetMapping("/kettle/opsInventoryTypeDdsAllTrans")
    public ResultVo<String> opsInventoryTypeDdsAllTrans();

    /**
     * 全量同步ops_inventory_dds
     */
    @GetMapping("/kettle/opsInventoryDdsAllTrans")
    public ResultVo<String> opsInventoryDdsAllTrans();

    /**
     * 全量同步 ops_inventory_property_dds
     */
    @GetMapping("/kettle/opsInventoryPropertyDdsAllTrans")
    public ResultVo<String> opsInventoryPropertyDdsAllTrans();

    /**
     * 同步指定作业
     */
    @GetMapping("/kettle/syncTransWithPath")
    public ResultVo<String> syncTransWithPath(@RequestParam("transName") String transName);


    /**
     * 全量同步inventory_supplier_dds
     */
    @GetMapping("/kettle/inventorySupplierDdsAllTrans")
    public ResultVo<String> inventorySupplierDdsAllTrans();

    /**
     * 增量同步inventory_supplier_dds_increment
     */
    @GetMapping("/kettle/inventorySupplierDdsIncrementTrans")
    public ResultVo<String> inventorySupplierDdsIncrementTrans();

}
