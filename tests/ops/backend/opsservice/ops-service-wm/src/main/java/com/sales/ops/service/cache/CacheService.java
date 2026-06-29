package com.sales.ops.service.cache;

import com.sales.ops.db.entity.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/4/23 10:17
 */
public interface CacheService {
    void saveBaseCache();

    OpsInventoryType getInvType(String inventoryTypeCode);

    OpsInventoryMatchConfig getMatchConfig(String inventoryMatchCode);

    List<OpsOrderInventoryRuleConfig> getOrderRuleConfig(String orderType, String tag,
                                                         String propertyType, String dlvEntire);

    OpsInventoryRuleConfig getRuleConfig(String ruleCode);

    List<OpsInventoryRuleConfigItem> getRuleConfigItem(String ruleCode);

    Integer hashOrderRuleKey(String orderType, String tag,
                             String propertyType, String dlvEntire);
}
