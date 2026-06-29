package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDoMapper {
    long countByExample(OpsDoExample example);

    int deleteByExample(OpsDoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDo record);

    int insertSelective(OpsDo record);

    List<OpsDo> selectByExample(OpsDoExample example);

    OpsDo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDo record, @Param("example") OpsDoExample example);

    int updateByExample(@Param("record") OpsDo record, @Param("example") OpsDoExample example);

    int updateByPrimaryKeySelective(OpsDo record);

    int updateByPrimaryKey(OpsDo record);
}