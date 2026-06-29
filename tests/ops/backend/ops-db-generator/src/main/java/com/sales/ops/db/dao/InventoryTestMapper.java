package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InventoryTest;
import com.sales.ops.db.entity.InventoryTestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryTestMapper {
    long countByExample(InventoryTestExample example);

    int deleteByExample(InventoryTestExample example);

    int insert(InventoryTest record);

    int insertSelective(InventoryTest record);

    List<InventoryTest> selectByExample(InventoryTestExample example);

    int updateByExampleSelective(@Param("record") InventoryTest record, @Param("example") InventoryTestExample example);

    int updateByExample(@Param("record") InventoryTest record, @Param("example") InventoryTestExample example);
}