package com.sales.ops.db.dao;

import com.sales.ops.db.entity.DboInventoryAgent;
import com.sales.ops.db.entity.DboInventoryAgentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DboInventoryAgentMapper {
    long countByExample(DboInventoryAgentExample example);

    int deleteByExample(DboInventoryAgentExample example);

    int insert(DboInventoryAgent record);

    int insertSelective(DboInventoryAgent record);

    List<DboInventoryAgent> selectByExampleWithBLOBs(DboInventoryAgentExample example);

    List<DboInventoryAgent> selectByExample(DboInventoryAgentExample example);

    int updateByExampleSelective(@Param("record") DboInventoryAgent record, @Param("example") DboInventoryAgentExample example);

    int updateByExampleWithBLOBs(@Param("record") DboInventoryAgent record, @Param("example") DboInventoryAgentExample example);

    int updateByExample(@Param("record") DboInventoryAgent record, @Param("example") DboInventoryAgentExample example);
}