package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoAutosendRemarkConfig;
import com.sales.ops.db.entity.OpsPoAutosendRemarkConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoAutosendRemarkConfigMapper {
    long countByExample(OpsPoAutosendRemarkConfigExample example);

    int deleteByExample(OpsPoAutosendRemarkConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPoAutosendRemarkConfig record);

    int insertSelective(OpsPoAutosendRemarkConfig record);

    List<OpsPoAutosendRemarkConfig> selectByExample(OpsPoAutosendRemarkConfigExample example);

    OpsPoAutosendRemarkConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPoAutosendRemarkConfig record, @Param("example") OpsPoAutosendRemarkConfigExample example);

    int updateByExample(@Param("record") OpsPoAutosendRemarkConfig record, @Param("example") OpsPoAutosendRemarkConfigExample example);

    int updateByPrimaryKeySelective(OpsPoAutosendRemarkConfig record);

    int updateByPrimaryKey(OpsPoAutosendRemarkConfig record);
}