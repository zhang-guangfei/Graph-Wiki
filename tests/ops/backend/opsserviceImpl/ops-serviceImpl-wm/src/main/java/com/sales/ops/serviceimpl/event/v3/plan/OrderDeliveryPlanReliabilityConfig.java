package com.sales.ops.serviceimpl.event.v3.plan;

import com.sales.ops.db.dao.OpsDeliveryPlanReliabilityConfigMapper;
import com.sales.ops.db.entity.OpsDeliveryPlanReliabilityConfig;
import com.sales.ops.db.entity.OpsDeliveryPlanReliabilityConfigExample;
import com.sales.ops.db.entity.OpsDeliveryPlanResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Configuration
public class OrderDeliveryPlanReliabilityConfig {

    private final Integer minReliabilityPiont = 60;

    private final Map<String, Integer> configMap = new HashMap<>();
    @Autowired
    private OpsDeliveryPlanReliabilityConfigMapper opsDeliveryPlanReliabilityConfigMapper;

    public void refreshConfigMap(List<OpsDeliveryPlanReliabilityConfig> list) {
        configMap.clear();
        for (OpsDeliveryPlanReliabilityConfig config : list) {
            if(StringUtils.isNotBlank(config.getCurrentCycle())&&StringUtils.isNotBlank(config.getCurrentCyclePoint())){
                String key = String.format("%s:%s", config.getCurrentCycle(), config.getCurrentCyclePoint());
                if (!configMap.containsKey(key)) {
                    configMap.put(key, config.getReliability());
                }
            }
        }
        log.info("已刷新交付计划可信度配置表");
    }

    public Integer getConfig(String key) {
        if (key == null) {
            return null;
        }
        if (configMap.containsKey(key)) {
            return configMap.get(key);
        } else {
            List<OpsDeliveryPlanReliabilityConfig> list = selectDeliveryPlanReliabilityConfig();
            refreshConfigMap(list);
            if (configMap.containsKey(key)) {
                return configMap.get(key);
            }
            return null;
        }
    }


    public Integer getConfig(OpsDeliveryPlanResult result) {
        if(StringUtils.isNotBlank(result.getCurrentCycle())&&StringUtils.isNotBlank(result.getCurrentCyclePoint())){
            String key = String.format("%s:%s", result.getCurrentCycle(), result.getCurrentCyclePoint());
            return getConfig(key);
        }
        return null;
    }

    public List<OpsDeliveryPlanReliabilityConfig> selectDeliveryPlanReliabilityConfig() {
        OpsDeliveryPlanReliabilityConfigExample ex = new OpsDeliveryPlanReliabilityConfigExample();
        ex.createCriteria().andDelflagEqualTo(0);
        return opsDeliveryPlanReliabilityConfigMapper.selectByExample(ex);
    }

    public boolean reliable(Integer reliabilityValue){
        return reliabilityValue != null && reliabilityValue >= minReliabilityPiont;
    }


}
