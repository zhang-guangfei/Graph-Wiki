package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryType;
import com.sales.ops.db.entity.OpsInventoryTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryTypeMapper {
    long countByExample(OpsInventoryTypeExample example);

    int deleteByExample(OpsInventoryTypeExample example);

    int deleteByPrimaryKey(String inventoryTypeCode);

    int insert(OpsInventoryType record);

    int insertSelective(OpsInventoryType record);

    List<OpsInventoryType> selectByExample(OpsInventoryTypeExample example);

    OpsInventoryType selectByPrimaryKey(String inventoryTypeCode);

    int updateByExampleSelective(@Param("record") OpsInventoryType record, @Param("example") OpsInventoryTypeExample example);

    int updateByExample(@Param("record") OpsInventoryType record, @Param("example") OpsInventoryTypeExample example);

    int updateByPrimaryKeySelective(OpsInventoryType record);

    int updateByPrimaryKey(OpsInventoryType record);
}