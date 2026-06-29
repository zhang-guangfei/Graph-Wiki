package com.sales.ops.service.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsInventoryMatchConfig;
import com.sales.ops.db.entity.OpsInventoryRuleConfig;
import com.sales.ops.db.entity.OpsInventoryRuleConfigItem;
import com.sales.ops.db.entity.OpsOrderInventoryRuleConfig;
import com.sales.ops.dto.inventory.InventoryCkByOrderInputDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.CKTYPEEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：库存基础数据属性查询
 * @date ：Created in 2021/12/9 11:57
 */
public interface OpsInventoryBaseService {
    @Transactional(rollbackFor = Exception.class)
    void subQtyInventoryForPre(Long id, int qty, int preQty, String tableType, UserDto userDto)
            throws OpsException;

    //查询匹配库存的维度
    OpsInventoryMatchConfig getOpsInventoryMatchConfig(String inventoryMatchCode);
    //根据ruleCode获取出库规则
    OpsInventoryRuleConfig getOpsInventoryRuleConfigList(String ruleCode);
    //根据ruleCode获取出库规则明细
    List<OpsInventoryRuleConfigItem> getInventoryRuleConfigItemList(String ruleCode);
    //获取单据类型和单据标记对应的出库规
    List<OpsOrderInventoryRuleConfig> getOpsOrderInventoryRuleConfigList(String orderType, String tag, String propertyType, CKTYPEEnum cktypeEnum);
    //刷新缓存
    String refreshOpsInventoryRuleAndConfigData(String mi);

}
