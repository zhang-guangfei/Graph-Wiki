package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Inventory;
import com.sales.ops.db.entity.InventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryMapper {
    long countByExample(InventoryExample example);

    int deleteByExample(InventoryExample example);

    int insert(Inventory record);

    int insertSelective(Inventory record);

    List<Inventory> selectByExampleWithBLOBs(InventoryExample example);

    List<Inventory> selectByExample(InventoryExample example);

    int updateByExampleSelective(@Param("record") Inventory record, @Param("example") InventoryExample example);

    int updateByExampleWithBLOBs(@Param("record") Inventory record, @Param("example") InventoryExample example);

    int updateByExample(@Param("record") Inventory record, @Param("example") InventoryExample example);
}