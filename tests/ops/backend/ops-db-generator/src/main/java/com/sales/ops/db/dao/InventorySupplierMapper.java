package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InventorySupplier;
import com.sales.ops.db.entity.InventorySupplierExample;
import com.sales.ops.db.entity.InventorySupplierKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventorySupplierMapper {
    long countByExample(InventorySupplierExample example);

    int deleteByExample(InventorySupplierExample example);

    int deleteByPrimaryKey(InventorySupplierKey key);

    int insert(InventorySupplier record);

    int insertSelective(InventorySupplier record);

    List<InventorySupplier> selectByExample(InventorySupplierExample example);

    InventorySupplier selectByPrimaryKey(InventorySupplierKey key);

    int updateByExampleSelective(@Param("record") InventorySupplier record, @Param("example") InventorySupplierExample example);

    int updateByExample(@Param("record") InventorySupplier record, @Param("example") InventorySupplierExample example);

    int updateByPrimaryKeySelective(InventorySupplier record);

    int updateByPrimaryKey(InventorySupplier record);
}