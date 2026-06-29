package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.inventory.*;
import com.smc.smccloud.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RequestMapping("/product/inventory")
@RestController
public class InventoryController {

    @Resource
    private InventoryService inventoryService;

    /**
     * 查询型号在仓库的通用在库可用数量和在途数量
     *
     * @param dto modelNos && warehouseCode
     * @return 型号在仓库的通用在库可用数量和在途数量
     */
    @RequestMapping(value = "/listModelWarehouseStock", method = RequestMethod.POST)
    public ResultVo<List<ModelWarehouseStockVO>> listModelWarehouseStock(@RequestBody ModelWarehouseStockRequest dto) {
        return inventoryService.listModelWarehouseStock(dto);
    }

    @RequestMapping(value = "/syncZeroInventory", method = RequestMethod.GET)
    public ResultVo<String> syncZeroInventory(@RequestParam("calcDate") Date calcDate) {
        return inventoryService.syncZeroInventory(calcDate);
    }

    /**
     * 库存查询
     */
    @RequestMapping(value = "/getModelInventoryByWarehouse", method = RequestMethod.GET)
    public ResultVo<List<SMSInventoryVO>> getModelInventoryByWarehouse(@RequestParam("modelNo") String modelNo){
        return inventoryService.getModelInventoryByWarehouse(modelNo);
    }

    /**
     * update by LiYingChao from bugId 8758 in 2022-11-23
     * 统计专备在库查询
     */
    @PostMapping("/findUserStockStatisList")
    public ResultVo<PageInfo<SpecStatisticsVO>> findUserStockStatisList(@RequestBody SpecStatisticsRequest request,
                                                                       @RequestParam("pageNumber") int pageNumber,
                                                                       @RequestParam("pageSize") int pageSize) {
        return inventoryService.findUserStockStatisList(request, pageNumber, pageSize);
    }

}
