package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsChangeOrderLog;
import com.sales.ops.db.entity.OpsChangeOrderLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsChangeOrderLogMapper {
    long countByExample(OpsChangeOrderLogExample example);

    int deleteByExample(OpsChangeOrderLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsChangeOrderLog record);

    int insertSelective(OpsChangeOrderLog record);

    List<OpsChangeOrderLog> selectByExample(OpsChangeOrderLogExample example);

    OpsChangeOrderLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsChangeOrderLog record, @Param("example") OpsChangeOrderLogExample example);

    int updateByExample(@Param("record") OpsChangeOrderLog record, @Param("example") OpsChangeOrderLogExample example);

    int updateByPrimaryKeySelective(OpsChangeOrderLog record);

    int updateByPrimaryKey(OpsChangeOrderLog record);
}