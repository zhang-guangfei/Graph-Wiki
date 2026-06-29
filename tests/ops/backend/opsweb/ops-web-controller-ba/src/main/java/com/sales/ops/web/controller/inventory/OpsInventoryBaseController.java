package com.sales.ops.web.controller.inventory;

import com.sales.ops.db.entity.OpsInventoryMatchConfig;
import com.sales.ops.db.entity.OpsInventoryRuleConfig;
import com.sales.ops.db.entity.OpsInventoryRuleConfigItem;
import com.sales.ops.db.entity.OpsOrderInventoryRuleConfig;
import com.sales.ops.dto.inventory.InventoryCkByOrderInputDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.ResultCode;
import com.sales.ops.enums.CKTYPEEnum;
import com.sales.ops.service.inventory.OpsInventoryBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：库存技术数据属性查询
 * @date ：Created in 2021/12/9 12:02
 */
@CrossOrigin
@RestController
@RequestMapping("/inventoryBase")
public class OpsInventoryBaseController {

    @Autowired
    private OpsInventoryBaseService opsInventoryBaseService;

    //查询匹配库存的维度
    @RequestMapping("/opsInventoryMatchConfig")
    public CommonResult<OpsInventoryMatchConfig> getOpsInventoryMatchConfig(@RequestParam String inventoryMatchCode) {

        try {
            OpsInventoryMatchConfig result = opsInventoryBaseService.getOpsInventoryMatchConfig(inventoryMatchCode);
            return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }
    //根据ruleCode获取出库规则
    @RequestMapping("/opsInventoryRuleConfig")
    public CommonResult<OpsInventoryRuleConfig> getOpsInventoryRuleConfigList(@RequestParam String ruleCode) {
        try {
            OpsInventoryRuleConfig result = opsInventoryBaseService.getOpsInventoryRuleConfigList(ruleCode);
            return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    //根据ruleCode获取出库规则明细
    @RequestMapping("/opsInventoryRuleConfigItem")
    public CommonResult<List<OpsInventoryRuleConfigItem>> getInventoryRuleConfigItemList(@RequestParam String ruleCode) {
        try {
            List<OpsInventoryRuleConfigItem> list = opsInventoryBaseService.getInventoryRuleConfigItemList(ruleCode);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    //获取单据类型和单据标记对应的出库规
    @RequestMapping("/opsOrderInventoryRuleConfig")
    public CommonResult<List<OpsOrderInventoryRuleConfig>> getOpsOrderInventoryRuleConfigList(@RequestParam("orderType") String orderType,
                                                                                              @RequestParam(value = "tag",required = false) String tag,
                                                                                              @RequestParam(value = "propertyType", required = false) String propertyType,
                                                                                              @RequestParam(value = "cktypeEnum", required = false) CKTYPEEnum cktypeEnum ) {
        try {
            List<OpsOrderInventoryRuleConfig> list = opsInventoryBaseService.getOpsOrderInventoryRuleConfigList(orderType, tag, propertyType,cktypeEnum);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }


    /**
     * 刷新以上方法缓存     *
     * @param mi 分钟
     * @return
     */
    @RequestMapping("/opsInventoryRuleAndConfig/refresh")
    public CommonResult refreshOpsInventoryRuleAndConfigData(@RequestParam String mi) {
        try {
            String result = opsInventoryBaseService.refreshOpsInventoryRuleAndConfigData(mi);
            return StringUtils.isBlank(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }


}
