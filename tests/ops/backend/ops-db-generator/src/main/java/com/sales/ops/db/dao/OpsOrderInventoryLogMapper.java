package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderInventoryLog;
import com.sales.ops.db.entity.OpsOrderInventoryLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderInventoryLogMapper {
    long countByExample(OpsOrderInventoryLogExample example);

    int deleteByExample(OpsOrderInventoryLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsOrderInventoryLog record);

    int insertSelective(OpsOrderInventoryLog record);

    List<OpsOrderInventoryLog> selectByExample(OpsOrderInventoryLogExample example);

    OpsOrderInventoryLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsOrderInventoryLog record, @Param("example") OpsOrderInventoryLogExample example);

    int updateByExample(@Param("record") OpsOrderInventoryLog record, @Param("example") OpsOrderInventoryLogExample example);

    int updateByPrimaryKeySelective(OpsOrderInventoryLog record);

    int updateByPrimaryKey(OpsOrderInventoryLog record);
}