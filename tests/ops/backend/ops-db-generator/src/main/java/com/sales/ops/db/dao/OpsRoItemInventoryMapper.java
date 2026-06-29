package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRoItemInventory;
import com.sales.ops.db.entity.OpsRoItemInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRoItemInventoryMapper {
    long countByExample(OpsRoItemInventoryExample example);

    int deleteByExample(OpsRoItemInventoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsRoItemInventory record);

    int insertSelective(OpsRoItemInventory record);

    List<OpsRoItemInventory> selectByExample(OpsRoItemInventoryExample example);

    OpsRoItemInventory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsRoItemInventory record, @Param("example") OpsRoItemInventoryExample example);

    int updateByExample(@Param("record") OpsRoItemInventory record, @Param("example") OpsRoItemInventoryExample example);

    int updateByPrimaryKeySelective(OpsRoItemInventory record);

    int updateByPrimaryKey(OpsRoItemInventory record);
}