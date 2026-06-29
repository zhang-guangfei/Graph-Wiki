package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InventoryTemp;
import com.sales.ops.db.entity.InventoryTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryTempMapper {
    long countByExample(InventoryTempExample example);

    int deleteByExample(InventoryTempExample example);

    int insert(InventoryTemp record);

    int insertSelective(InventoryTemp record);

    List<InventoryTemp> selectByExample(InventoryTempExample example);

    int updateByExampleSelective(@Param("record") InventoryTemp record, @Param("example") InventoryTempExample example);

    int updateByExample(@Param("record") InventoryTemp record, @Param("example") InventoryTempExample example);
}