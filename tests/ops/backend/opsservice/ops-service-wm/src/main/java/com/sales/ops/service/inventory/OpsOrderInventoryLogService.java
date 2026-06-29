package com.sales.ops.service.inventory;

import com.sales.ops.db.entity.OpsOrderInventoryLog;
import com.sales.ops.dto.inventory.InventoryCkByOrderInputDto;
import com.sales.ops.dto.inventory.InventoryCkByOrderOutDto;

/**
 * @author ：c02483
 * @date ：Created in 2022/1/25 16:44
 * @description：记录原始分配库存记录信息
 */
public interface OpsOrderInventoryLogService {

    void addOrderInventoryLog(InventoryCkByOrderInputDto inputDto, InventoryCkByOrderOutDto outDto);
}
