package com.sales.ops.feign.inventory;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.InventoryCkByOrderInputDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.CKTYPEEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：仓库基础数据查询增加缓存及更新
 * @date ：Created in 2021/12/9 13:58
 */
@FeignClient(name = "ba-service",contextId="inventory-base")
public interface OpsInventoryBaseFeignApi {

    /**
     * 根据inventoryMatchCode 查询匹配库存的维度（数据库或缓存）
     * @param inventoryMatchCode
     * @return
     */
    @RequestMapping(value = "/inventoryBase/opsInventoryMatchConfig",method = RequestMethod.GET)
    CommonResult<OpsInventoryMatchConfig> getOpsInventoryMatchConfig(@RequestParam("inventoryMatchCode") String inventoryMatchCode);

    /**
     * 根据ruleCode获取出库规则 查询匹配库存的维度（数据库或缓存）
     * @param ruleCode
     * @return
     */
    @RequestMapping(value = "/inventoryBase/opsInventoryRuleConfig",method = RequestMethod.GET)
    CommonResult<OpsInventoryRuleConfig> getOpsInventoryRuleConfigList(@RequestParam("ruleCode") String ruleCode);

    /**
     * 根据ruleCode获取出库规则明细（数据库或缓存）
     * @param ruleCode
     * @return
     */
    @RequestMapping(value = "/inventoryBase/opsInventoryRuleConfigItem",method = RequestMethod.GET)
    CommonResult<List<OpsInventoryRuleConfigItem>> getInventoryRuleConfigItemList(@RequestParam("ruleCode") String ruleCode);


    /**
     * 获取单据类型和单据标记对应的出库规（数据库或缓存）
     * @param
     * @return
     */
    @RequestMapping(value = "/inventoryBase/opsOrderInventoryRuleConfig",method = RequestMethod.GET)
    CommonResult<List<OpsOrderInventoryRuleConfig>> getOpsOrderInventoryRuleConfigList(@RequestParam("orderType") String orderType,
                                                                                       @RequestParam(value = "tag",required = false) String tag,
                                                                                       @RequestParam(value = "propertyType",required = false) String propertyType,
                                                                                       @RequestParam(value = "cktypeEnum", required = false) CKTYPEEnum cktypeEnum);


    /**
     * job任务 定时刷新缓存 删除redis mi 分钟的数据
     * @param mi 最近分钟
     * @return
     */
    @RequestMapping(value = "/inventoryBase/opsInventoryRuleAndConfig/refresh",method = RequestMethod.GET)
    CommonResult<String> refreshOpsInventoryRuleAndConfigData(@RequestParam("mi") String mi);


}
