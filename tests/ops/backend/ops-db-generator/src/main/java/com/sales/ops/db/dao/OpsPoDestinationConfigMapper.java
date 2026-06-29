package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoDestinationConfig;
import com.sales.ops.db.entity.OpsPoDestinationConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoDestinationConfigMapper {
    long countByExample(OpsPoDestinationConfigExample example);

    int deleteByExample(OpsPoDestinationConfigExample example);

    int insert(OpsPoDestinationConfig record);

    int insertSelective(OpsPoDestinationConfig record);

    List<OpsPoDestinationConfig> selectByExample(OpsPoDestinationConfigExample example);

    int updateByExampleSelective(@Param("record") OpsPoDestinationConfig record, @Param("example") OpsPoDestinationConfigExample example);

    int updateByExample(@Param("record") OpsPoDestinationConfig record, @Param("example") OpsPoDestinationConfigExample example);
}