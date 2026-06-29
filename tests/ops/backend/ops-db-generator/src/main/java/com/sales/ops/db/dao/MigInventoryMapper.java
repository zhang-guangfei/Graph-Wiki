package com.sales.ops.db.dao;

import com.sales.ops.db.entity.MigInventory;
import com.sales.ops.db.entity.MigInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MigInventoryMapper {
    long countByExample(MigInventoryExample example);

    int deleteByExample(MigInventoryExample example);

    int insert(MigInventory record);

    int insertSelective(MigInventory record);

    List<MigInventory> selectByExample(MigInventoryExample example);

    int updateByExampleSelective(@Param("record") MigInventory record, @Param("example") MigInventoryExample example);

    int updateByExample(@Param("record") MigInventory record, @Param("example") MigInventoryExample example);
}