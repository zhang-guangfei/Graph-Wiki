package com.sales.ops.service.inventory.adjustment;

import com.sales.ops.common.opsexception.OpsException;

/**
 * @author C12961
 * @date 2022/11/9 14:33
 */
public interface CalInventoryQtyService {


    void calInventoryQty(Long diffId, String warehouse, String modelno, Integer quantity) throws OpsException;
}
