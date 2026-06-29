package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPcoItemInventory;
import com.sales.ops.db.entity.OpsPcoItemInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPcoItemInventoryMapper {
    long countByExample(OpsPcoItemInventoryExample example);

    int deleteByExample(OpsPcoItemInventoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPcoItemInventory record);

    int insertSelective(OpsPcoItemInventory record);

    List<OpsPcoItemInventory> selectByExample(OpsPcoItemInventoryExample example);

    OpsPcoItemInventory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPcoItemInventory record, @Param("example") OpsPcoItemInventoryExample example);

    int updateByExample(@Param("record") OpsPcoItemInventory record, @Param("example") OpsPcoItemInventoryExample example);

    int updateByPrimaryKeySelective(OpsPcoItemInventory record);

    int updateByPrimaryKey(OpsPcoItemInventory record);
}