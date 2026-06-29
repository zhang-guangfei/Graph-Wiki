package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryLog;
import com.sales.ops.db.entity.OpsInventoryLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryLogMapper {
    long countByExample(OpsInventoryLogExample example);

    int deleteByExample(OpsInventoryLogExample example);

    int deleteByPrimaryKey(Long inventoryLogId);

    int insert(OpsInventoryLog record);

    int insertSelective(OpsInventoryLog record);

    List<OpsInventoryLog> selectByExample(OpsInventoryLogExample example);

    OpsInventoryLog selectByPrimaryKey(Long inventoryLogId);

    int updateByExampleSelective(@Param("record") OpsInventoryLog record, @Param("example") OpsInventoryLogExample example);

    int updateByExample(@Param("record") OpsInventoryLog record, @Param("example") OpsInventoryLogExample example);

    int updateByPrimaryKeySelective(OpsInventoryLog record);

    int updateByPrimaryKey(OpsInventoryLog record);
}