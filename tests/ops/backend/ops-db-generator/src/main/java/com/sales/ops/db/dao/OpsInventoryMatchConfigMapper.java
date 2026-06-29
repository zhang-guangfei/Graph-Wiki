package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryMatchConfig;
import com.sales.ops.db.entity.OpsInventoryMatchConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryMatchConfigMapper {
    long countByExample(OpsInventoryMatchConfigExample example);

    int deleteByExample(OpsInventoryMatchConfigExample example);

    int deleteByPrimaryKey(String inventoryMatchCode);

    int insert(OpsInventoryMatchConfig record);

    int insertSelective(OpsInventoryMatchConfig record);

    List<OpsInventoryMatchConfig> selectByExample(OpsInventoryMatchConfigExample example);

    OpsInventoryMatchConfig selectByPrimaryKey(String inventoryMatchCode);

    int updateByExampleSelective(@Param("record") OpsInventoryMatchConfig record, @Param("example") OpsInventoryMatchConfigExample example);

    int updateByExample(@Param("record") OpsInventoryMatchConfig record, @Param("example") OpsInventoryMatchConfigExample example);

    int updateByPrimaryKeySelective(OpsInventoryMatchConfig record);

    int updateByPrimaryKey(OpsInventoryMatchConfig record);
}