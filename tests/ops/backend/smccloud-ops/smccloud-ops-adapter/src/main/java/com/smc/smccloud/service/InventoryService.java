package com.smc.smccloud.service;

import com.sales.ops.common.opsexception.OpsException;
import com.smc.smccloud.model.stock.InventoryCondition;
import com.smc.smccloud.model.stock.InventoryListDto;

import java.util.List;

public interface InventoryService {

    List<String> randomModelno(Integer num);

    List<InventoryListDto> searchInventory(InventoryCondition condition) throws OpsException;

}
