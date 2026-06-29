package com.sales.ops.db.dao;

import com.sales.ops.db.entity.MigInventoryAgent;
import com.sales.ops.db.entity.MigInventoryAgentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MigInventoryAgentMapper {
    long countByExample(MigInventoryAgentExample example);

    int deleteByExample(MigInventoryAgentExample example);

    int insert(MigInventoryAgent record);

    int insertSelective(MigInventoryAgent record);

    List<MigInventoryAgent> selectByExampleWithBLOBs(MigInventoryAgentExample example);

    List<MigInventoryAgent> selectByExample(MigInventoryAgentExample example);

    int updateByExampleSelective(@Param("record") MigInventoryAgent record, @Param("example") MigInventoryAgentExample example);

    int updateByExampleWithBLOBs(@Param("record") MigInventoryAgent record, @Param("example") MigInventoryAgentExample example);

    int updateByExample(@Param("record") MigInventoryAgent record, @Param("example") MigInventoryAgentExample example);
}