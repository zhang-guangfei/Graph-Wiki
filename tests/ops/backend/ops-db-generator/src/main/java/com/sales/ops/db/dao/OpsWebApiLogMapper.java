package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsWebApiLog;
import com.sales.ops.db.entity.OpsWebApiLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsWebApiLogMapper {
    long countByExample(OpsWebApiLogExample example);

    int deleteByExample(OpsWebApiLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsWebApiLog record);

    int insertSelective(OpsWebApiLog record);

    List<OpsWebApiLog> selectByExample(OpsWebApiLogExample example);

    OpsWebApiLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsWebApiLog record, @Param("example") OpsWebApiLogExample example);

    int updateByExample(@Param("record") OpsWebApiLog record, @Param("example") OpsWebApiLogExample example);

    int updateByPrimaryKeySelective(OpsWebApiLog record);

    int updateByPrimaryKey(OpsWebApiLog record);
}