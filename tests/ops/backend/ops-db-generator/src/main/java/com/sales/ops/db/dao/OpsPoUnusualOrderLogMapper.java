package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoUnusualOrderLog;
import com.sales.ops.db.entity.OpsPoUnusualOrderLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoUnusualOrderLogMapper {
    long countByExample(OpsPoUnusualOrderLogExample example);

    int deleteByExample(OpsPoUnusualOrderLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPoUnusualOrderLog record);

    int insertSelective(OpsPoUnusualOrderLog record);

    List<OpsPoUnusualOrderLog> selectByExample(OpsPoUnusualOrderLogExample example);

    OpsPoUnusualOrderLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPoUnusualOrderLog record, @Param("example") OpsPoUnusualOrderLogExample example);

    int updateByExample(@Param("record") OpsPoUnusualOrderLog record, @Param("example") OpsPoUnusualOrderLogExample example);

    int updateByPrimaryKeySelective(OpsPoUnusualOrderLog record);

    int updateByPrimaryKey(OpsPoUnusualOrderLog record);
}