package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryMapper {
    long countByExample(OpsInventoryExample example);

    int deleteByExample(OpsInventoryExample example);

    int deleteByPrimaryKey(Long inventoryId);

    int insert(OpsInventory record);

    int insertSelective(OpsInventory record);

    List<OpsInventory> selectByExample(OpsInventoryExample example);

    OpsInventory selectByPrimaryKey(Long inventoryId);

    int updateByExampleSelective(@Param("record") OpsInventory record, @Param("example") OpsInventoryExample example);

    int updateByExample(@Param("record") OpsInventory record, @Param("example") OpsInventoryExample example);

    int updateByPrimaryKeySelective(OpsInventory record);

    int updateByPrimaryKey(OpsInventory record);
}