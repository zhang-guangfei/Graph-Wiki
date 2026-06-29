package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderAssignResult;
import com.sales.ops.db.entity.OpsOrderAssignResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderAssignResultMapper {
    long countByExample(OpsOrderAssignResultExample example);

    int deleteByExample(OpsOrderAssignResultExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsOrderAssignResult record);

    int insertSelective(OpsOrderAssignResult record);

    List<OpsOrderAssignResult> selectByExample(OpsOrderAssignResultExample example);

    OpsOrderAssignResult selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsOrderAssignResult record, @Param("example") OpsOrderAssignResultExample example);

    int updateByExample(@Param("record") OpsOrderAssignResult record, @Param("example") OpsOrderAssignResultExample example);

    int updateByPrimaryKeySelective(OpsOrderAssignResult record);

    int updateByPrimaryKey(OpsOrderAssignResult record);
}