package com.sales.ops.service.ba;

import com.sales.ops.db.entity.OpsWarehouse;

/**
 * @author C12961
 * @date 2023/2/16 19:32
 */
public interface BaseWarehouseService {


    OpsWarehouse getWarehouseByCode(String code);

    boolean isMaster(String warehouseType);

    boolean isWT(String warehouseType);
}
