package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InventoryGz;
import com.sales.ops.db.entity.InventoryGzExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryGzMapper {
    long countByExample(InventoryGzExample example);

    int deleteByExample(InventoryGzExample example);

    int insert(InventoryGz record);

    int insertSelective(InventoryGz record);

    List<InventoryGz> selectByExample(InventoryGzExample example);

    int updateByExampleSelective(@Param("record") InventoryGz record, @Param("example") InventoryGzExample example);

    int updateByExample(@Param("record") InventoryGz record, @Param("example") InventoryGzExample example);
}