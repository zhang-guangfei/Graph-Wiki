package com.smc.smccloud.service;

import com.sales.ops.dto.assembly.InventoryRelationOrderExcel;
import com.sales.ops.dto.query.InventoryQO;

import java.util.List;

public interface ExportInventoryRelationOrderService {
    List<InventoryRelationOrderExcel> SyncGetInventoryRelationOrder(InventoryQO condition);

    List<InventoryRelationOrderExcel> AsyncGetInventoryRelationOrder(InventoryQO condition);
}
