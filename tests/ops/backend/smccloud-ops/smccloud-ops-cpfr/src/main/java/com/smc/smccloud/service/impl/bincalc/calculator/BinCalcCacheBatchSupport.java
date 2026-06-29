package com.smc.smccloud.service.impl.bincalc.calculator;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.smc.smccloud.model.binorder.BinOrderDetailDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Task 1、Task 7A：
 * 批量缓存加载的辅助方法，负责把一次批量查询结果恢复成原缓存使用的 modelNo -> list 结构。
 */
final class BinCalcCacheBatchSupport {

    private BinCalcCacheBatchSupport() {
    }

    static List<String> distinctModelNos(List<BinOrderDetailDO> details) {
        Set<String> modelNos = new LinkedHashSet<>(hashMapCapacity(details == null ? 0 : details.size()));
        if (details == null) {
            return new ArrayList<>();
        }
        for (BinOrderDetailDO detail : details) {
            if (detail != null && detail.getModelNo() != null) {
                modelNos.add(detail.getModelNo());
            }
        }
        return new ArrayList<>(modelNos);
    }

    static Map<String, List<OpsInventory>> groupInventoryByModel(
            List<BinOrderDetailDO> details, List<OpsInventory> inventories) {
        Map<String, List<OpsInventory>> result = initModelMap(details);
        if (inventories == null) {
            return result;
        }
        for (OpsInventory inventory : inventories) {
            if (inventory == null) {
                continue;
            }
            List<OpsInventory> modelInventories = result.get(inventory.getModelno());
            if (modelInventories != null) {
                modelInventories.add(inventory);
            }
        }
        return result;
    }

    static Map<String, List<OpsInventoryMove>> groupInventoryMoveByModel(
            List<BinOrderDetailDO> details, List<OpsInventoryMove> inventoryMoves) {
        Map<String, List<OpsInventoryMove>> result = initModelMap(details);
        if (inventoryMoves == null) {
            return result;
        }
        for (OpsInventoryMove inventoryMove : inventoryMoves) {
            if (inventoryMove == null) {
                continue;
            }
            List<OpsInventoryMove> modelMoves = result.get(inventoryMove.getModelno());
            if (modelMoves != null) {
                modelMoves.add(inventoryMove);
            }
        }
        return result;
    }

    private static <T> Map<String, List<T>> initModelMap(List<BinOrderDetailDO> details) {
        Map<String, List<T>> result = new HashMap<>(hashMapCapacity(details == null ? 0 : details.size()));
        if (details == null) {
            return result;
        }
        for (BinOrderDetailDO detail : details) {
            if (detail != null && !result.containsKey(detail.getModelNo())) {
                // 先放入空列表，保持原逻辑中“无库存也有 model key”的缓存形态。
                result.put(detail.getModelNo(), new ArrayList<>());
            }
        }
        return result;
    }

    private static int hashMapCapacity(int expectedSize) {
        if (expectedSize <= 0) {
            return 1;
        }
        return (int) (expectedSize / 0.75f) + 1;
    }
}
