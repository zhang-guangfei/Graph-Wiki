package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderUpdateLog;
import com.sales.ops.db.entity.OpsOrderUpdateLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderUpdateLogMapper {
    long countByExample(OpsOrderUpdateLogExample example);

    int deleteByExample(OpsOrderUpdateLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsOrderUpdateLog record);

    int insertSelective(OpsOrderUpdateLog record);

    List<OpsOrderUpdateLog> selectByExample(OpsOrderUpdateLogExample example);

    OpsOrderUpdateLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsOrderUpdateLog record, @Param("example") OpsOrderUpdateLogExample example);

    int updateByExample(@Param("record") OpsOrderUpdateLog record, @Param("example") OpsOrderUpdateLogExample example);

    int updateByPrimaryKeySelective(OpsOrderUpdateLog record);

    int updateByPrimaryKey(OpsOrderUpdateLog record);
}