package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InventoryMoveTemp;
import com.sales.ops.db.entity.InventoryMoveTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryMoveTempMapper {
    long countByExample(InventoryMoveTempExample example);

    int deleteByExample(InventoryMoveTempExample example);

    int insert(InventoryMoveTemp record);

    int insertSelective(InventoryMoveTemp record);

    List<InventoryMoveTemp> selectByExample(InventoryMoveTempExample example);

    int updateByExampleSelective(@Param("record") InventoryMoveTemp record, @Param("example") InventoryMoveTempExample example);

    int updateByExample(@Param("record") InventoryMoveTemp record, @Param("example") InventoryMoveTempExample example);
}