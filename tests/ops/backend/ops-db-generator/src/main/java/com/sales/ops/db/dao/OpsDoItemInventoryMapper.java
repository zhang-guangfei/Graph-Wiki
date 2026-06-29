package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDoItemInventory;
import com.sales.ops.db.entity.OpsDoItemInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDoItemInventoryMapper {
    long countByExample(OpsDoItemInventoryExample example);

    int deleteByExample(OpsDoItemInventoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDoItemInventory record);

    int insertSelective(OpsDoItemInventory record);

    List<OpsDoItemInventory> selectByExample(OpsDoItemInventoryExample example);

    OpsDoItemInventory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDoItemInventory record, @Param("example") OpsDoItemInventoryExample example);

    int updateByExample(@Param("record") OpsDoItemInventory record, @Param("example") OpsDoItemInventoryExample example);

    int updateByPrimaryKeySelective(OpsDoItemInventory record);

    int updateByPrimaryKey(OpsDoItemInventory record);
}