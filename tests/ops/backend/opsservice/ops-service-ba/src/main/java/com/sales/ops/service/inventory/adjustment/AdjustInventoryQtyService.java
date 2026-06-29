package com.sales.ops.service.inventory.adjustment;

/**
 * @author C12961
 * @date 2022/11/4 15:40
 */
public interface AdjustInventoryQtyService {
    void createOpsInventoryDiff();

    void handleOpsInventoryDiff(Integer num);

    void handleOpsInventoryAdj(Integer num);
}
