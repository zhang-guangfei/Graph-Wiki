package com.sales.ops.serviceimpl.cache;

import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.CacheDao;
import com.sales.ops.service.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/4/23 10:17
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private CacheDao cacheDao;

    @Override
    public void saveBaseCache(){
        // 0. 清除库存缓存
        StaticMapUtil.clearAllMap();
        // 1. OpsInventoryType
        saveInventoryTypeCache();
        // 2. invMatchConfig
        saveMatchConfigCache();
        // 3. OpsOrderInventoryRuleConfig
        saveOrderRuleConfigCache();
        // 4. OpsInventoryRuleConfig
        saveRuleConfigCache();
        // 5. OpsInventoryRuleConfigItem
        saveRuleConfigItemCache();

    }

    @Override
    public OpsInventoryType getInvType(String inventoryTypeCode){
        return StaticMapUtil.invTypeMap.get(inventoryTypeCode);
    }

    @Override
    public OpsInventoryMatchConfig getMatchConfig(String inventoryMatchCode){
        return StaticMapUtil.invMatchConfig.get(inventoryMatchCode);
    }


    @Override
    public List<OpsOrderInventoryRuleConfig> getOrderRuleConfig(String orderType, String tag,
                                                                String propertyType, String dlvEntire){
        Integer key = hashOrderRuleKey(orderType, tag, propertyType, dlvEntire);
        return StaticMapUtil.orderRuleConfigMap.get(key);
    }

    @Override
    public OpsInventoryRuleConfig getRuleConfig(String ruleCode){
        return StaticMapUtil.invRuleConfig.get(ruleCode);
    }

    @Override
    public List<OpsInventoryRuleConfigItem> getRuleConfigItem(String ruleCode){
        return StaticMapUtil.invRuleConfigItemMap.get(ruleCode);
    }


    public void saveInventoryTypeCache(){
        List<OpsInventoryType> inventoryTypeList = cacheDao.getInventoryType();
        for (OpsInventoryType inventoryType : inventoryTypeList) {
            StaticMapUtil.invTypeMap.put(inventoryType.getInventoryTypeCode(), inventoryType);
        }
    }

    public void saveMatchConfigCache(){
        List<OpsInventoryMatchConfig> matchConfig = cacheDao.getMatchConfig();
        for (OpsInventoryMatchConfig opsInventoryMatchConfig : matchConfig) {
            StaticMapUtil.invMatchConfig.put(opsInventoryMatchConfig.getInventoryMatchCode(), opsInventoryMatchConfig);
        }
    }

    public void saveOrderRuleConfigCache(){
        List<OpsOrderInventoryRuleConfig> orderInventoryRuleConfigList = cacheDao.getOrderInventoryRuleConfig();
        for (OpsOrderInventoryRuleConfig config : orderInventoryRuleConfigList) {
            Integer key = hashOrderRuleKey(config.getOrderType(), config.getOrderTag()
                    , config.getPropertyType(), config.getDlvEntire());
            if (!StaticMapUtil.orderRuleConfigMap.containsKey(key)) {
                List<OpsOrderInventoryRuleConfig> list = new ArrayList<>();
                list.add(config);
                StaticMapUtil.orderRuleConfigMap.put(key,list );
            }else {
                StaticMapUtil.orderRuleConfigMap.get(key).add(config);
            }
        }
        for (Integer key : StaticMapUtil.orderRuleConfigMap.keySet()) {
            // map 中 value 为 list 排序 用sort字段 升序
            StaticMapUtil.orderRuleConfigMap.get(key).sort((o1, o2) -> o1.getSort() - o2.getSort());
        }
    }

    @Override
    public Integer hashOrderRuleKey(String orderType, String tag,
                                    String propertyType, String dlvEntire) {
        final int prime = 31;
        int result = 1;
        result = prime * result + (orderType.hashCode());
        result = prime * result + (tag.hashCode());
        result = prime * result + (propertyType.hashCode());
        result = prime * result + (dlvEntire.hashCode());
        return result;
    }

    public void saveRuleConfigCache(){
        List<OpsInventoryRuleConfig> ruleConfigList = cacheDao.getInventoryRuleConfig();
        for (OpsInventoryRuleConfig opsInventoryRuleConfig : ruleConfigList) {
            StaticMapUtil.invRuleConfig.put(opsInventoryRuleConfig.getRuleCode(), opsInventoryRuleConfig);
        }
    }

    public void saveRuleConfigItemCache(){
        List<OpsInventoryRuleConfigItem> ruleConfigItemList = cacheDao.getInventoryRuleConfigItem();
        for (OpsInventoryRuleConfigItem item : ruleConfigItemList) {
            if (!StaticMapUtil.invRuleConfigItemMap.containsKey(item.getRuleCode())) {
                List<OpsInventoryRuleConfigItem> list = new ArrayList<>();
                list.add(item);
                StaticMapUtil.invRuleConfigItemMap.put(item.getRuleCode(), list);
            }else {
                StaticMapUtil.invRuleConfigItemMap.get(item.getRuleCode()).add(item);
            }
        }
        for (String key : StaticMapUtil.invRuleConfigItemMap.keySet()) {
            // map 中 value 为 list 排序 用ruleSort字段 升序
            StaticMapUtil.invRuleConfigItemMap.get(key).sort((o1, o2) -> o1.getRuleSort() - o2.getRuleSort());
        }
    }
}
