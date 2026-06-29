package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.db.entity.OpsInventoryPropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryPropertyMapper {
    long countByExample(OpsInventoryPropertyExample example);

    int deleteByExample(OpsInventoryPropertyExample example);

    int deleteByPrimaryKey(Long inventoryPropertyId);

    int insert(OpsInventoryProperty record);

    int insertSelective(OpsInventoryProperty record);

    List<OpsInventoryProperty> selectByExample(OpsInventoryPropertyExample example);

    OpsInventoryProperty selectByPrimaryKey(Long inventoryPropertyId);

    int updateByExampleSelective(@Param("record") OpsInventoryProperty record, @Param("example") OpsInventoryPropertyExample example);

    int updateByExample(@Param("record") OpsInventoryProperty record, @Param("example") OpsInventoryPropertyExample example);

    int updateByPrimaryKeySelective(OpsInventoryProperty record);

    int updateByPrimaryKey(OpsInventoryProperty record);
}