package com.smc.smccloud.service;

import com.sales.ops.db.entity.OpsInventoryProperty;
import com.smc.smccloud.service.impl.InventoryPropertyServiceImpl;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface InventoryPropertyService {
    List<Long> getinventoryPropertyListByCustomerNo(String customerNo, String inventoryType);

    @Cacheable(cacheManager = "cacheManager", cacheNames = InventoryPropertyServiceImpl.PROPERTY_KEY_PREFIX, key = "#id")
    OpsInventoryProperty getInventoryProperty(Long id);

    @Cacheable(cacheManager = "cacheManager", cacheNames = InventoryPropertyServiceImpl.BIN_MODEL_KEY_PREFIX, key = "#modelNo")
    List<String> getBinData(String modelNo);
}
