package com.sales.ops.service.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsInventoryType;

import java.util.List;

public interface OpsInventoryTypeService {

    List<OpsInventoryType> findAllTypes();

    String findDescByType(String type)throws OpsException;

    OpsInventoryType findInventoryTypeByCode(String inventoryTypeCode);
}
