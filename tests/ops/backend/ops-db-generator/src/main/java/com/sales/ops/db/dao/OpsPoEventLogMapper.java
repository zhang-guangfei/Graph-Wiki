package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoEventLog;
import com.sales.ops.db.entity.OpsPoEventLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsPoEventLogMapper {
    long countByExample(OpsPoEventLogExample example);

    int deleteByExample(OpsPoEventLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPoEventLog record);

    int insertSelective(OpsPoEventLog record);

    List<OpsPoEventLog> selectByExample(OpsPoEventLogExample example);

    OpsPoEventLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPoEventLog record, @Param("example") OpsPoEventLogExample example);

    int updateByExample(@Param("record") OpsPoEventLog record, @Param("example") OpsPoEventLogExample example);

    int updateByPrimaryKeySelective(OpsPoEventLog record);

    int updateByPrimaryKey(OpsPoEventLog record);
}