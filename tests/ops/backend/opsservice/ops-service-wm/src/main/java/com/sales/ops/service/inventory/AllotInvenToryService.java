package com.sales.ops.service.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsInventoryRuleConfigItem;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.sales.ops.dto.common.BomSelectResult;
import com.sales.ops.dto.inventory.InventoryCkByOrderInputDto;
import com.sales.ops.dto.inventory.InventoryCkByOrderOutDto;
import com.sales.ops.dto.zd.ZDInventoryCkByOrderOutDto;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 分配库存算法
 * @date 2021/10/3 9:29
 */
public interface AllotInvenToryService {
    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库存属性---》库房遍历出库,整出规则
     */
    void PHUnAssInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList, InventoryCkByOrderOutDto outDto) throws OpsException;

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库存属性---》库房遍历出库,拆分数量规则
     */
    void PHAssQtyInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList, InventoryCkByOrderOutDto outDto) throws OpsException;

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库存属性---》库房遍历出库,拆分型号规则
     */
    void PHAssModelNoInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList,
                               InventoryCkByOrderOutDto outDto, BomSelectResult bom) throws OpsException;

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库房---》库存属性遍历出库,整出规则
     */
    void HPUnAssInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList, List<OpsWarehouseSalesbranchConfig> salesbranchConfigList, InventoryCkByOrderOutDto outDto) throws OpsException;

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库房---》库存属性遍历出库,拆分数量规则
     */
    void HPAssQtyInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList, List<OpsWarehouseSalesbranchConfig> salesbranchConfigList, InventoryCkByOrderOutDto outDto) throws OpsException;

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库房---》库存属性遍历出库,拆分型号规则
     */
    void HPAssModelNoInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList,
                               List<OpsWarehouseSalesbranchConfig> salesbranchConfigList, InventoryCkByOrderOutDto outDto,BomSelectResult bom) throws OpsException;

    void getInvByModelNO(InventoryCkByOrderInputDto inputDto, String modelNo);

    void getMoveInvByModelNO(InventoryCkByOrderInputDto inputDto, String modelNo);

    void minPack(String modelNo, InventoryCkByOrderInputDto inputDto, InventoryCkByOrderOutDto outDto)
            throws OpsException;

    /**
     * @author ：c02483
     * @date ：Created in 2021/12/30 10:58
     * @description：库存分配
     */
    InventoryCkByOrderOutDto getOpsInventoryListByCk(InventoryCkByOrderInputDto inputDto) throws OpsException;

    void getOpsInventoryListByCkToZD(InventoryCkByOrderInputDto inputDto,ZDInventoryCkByOrderOutDto out) throws OpsException;
}
