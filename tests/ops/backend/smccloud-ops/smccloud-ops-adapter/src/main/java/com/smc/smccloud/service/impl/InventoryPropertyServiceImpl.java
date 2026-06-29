package com.smc.smccloud.service.impl;

import com.sales.ops.db.entity.OpsInventoryProperty;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.mapper.OpsInventoryReadOnlyMapper;
import com.smc.smccloud.service.InventoryPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventoryPropertyServiceImpl implements InventoryPropertyService {


    @Autowired
    private OpsInventoryReadOnlyMapper opsInventoryReadOnlyMapper;
    @Autowired
    private RedisManager redisManager;

    public static final String PROPERTY_KEY_PREFIX = "ops:property";

    public static final String BIN_MODEL_KEY_PREFIX = "ops:binmodel";

    @Override
    public List<Long> getinventoryPropertyListByCustomerNo(String customerNo, String inventoryType) {
        return opsInventoryReadOnlyMapper.selectInventoryPropertyIdsByCustomerNo(customerNo, inventoryType);
    }

    @Cacheable(cacheManager = "cacheManager", cacheNames = PROPERTY_KEY_PREFIX, key = "#id")
    @Override
    public OpsInventoryProperty getInventoryProperty(Long id) {
        return opsInventoryReadOnlyMapper.selectInventoryProperty(id);
    }

    @Cacheable(cacheManager = "cacheManager", cacheNames = PROPERTY_KEY_PREFIX, key = "#ids")
    public Map<Long, OpsInventoryProperty> getInventoryPropertyList(List<Long> ids) {
        return opsInventoryReadOnlyMapper.selectInventoryPropertyList(ids).stream().collect(Collectors.toMap(OpsInventoryProperty::getInventoryPropertyId, item -> item));
    }

    @Scheduled(cron = "0 */30 * * * ?")
    public void loadAllInventoryProperty() {
        redisManager.del(PROPERTY_KEY_PREFIX);
        List<OpsInventoryProperty> propertyList = opsInventoryReadOnlyMapper.selectInventoryPropertyAll();
        for (OpsInventoryProperty property : propertyList) {
            redisManager.set(PROPERTY_KEY_PREFIX + "::" + property.getInventoryPropertyId(), property, 30 * 60);
        }
    }

    @PostConstruct
    public void start() {
        loadAllInventoryProperty();
    }


    @Cacheable(cacheManager = "cacheManager", cacheNames = BIN_MODEL_KEY_PREFIX, key = "#modelNo")
    @Override
    public List<String> getBinData(String modelNo) {
        return opsInventoryReadOnlyMapper.getBindata(modelNo);
    }

    @Cacheable(cacheManager = "cacheManager", cacheNames = BIN_MODEL_KEY_PREFIX, key = "#modelNo+'::'+#customerNo")
    public Boolean isBinModelno(String modelNo, String customerNo) {
        return opsInventoryReadOnlyMapper.isBinModelno(modelNo, customerNo) > 0;
    }


}
