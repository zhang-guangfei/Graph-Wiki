package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoModelSendConfig;
import com.sales.ops.db.entity.OpsPoModelSendConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoModelSendConfigMapper {
    long countByExample(OpsPoModelSendConfigExample example);

    int deleteByExample(OpsPoModelSendConfigExample example);

    int insert(OpsPoModelSendConfig record);

    int insertSelective(OpsPoModelSendConfig record);

    List<OpsPoModelSendConfig> selectByExample(OpsPoModelSendConfigExample example);

    int updateByExampleSelective(@Param("record") OpsPoModelSendConfig record, @Param("example") OpsPoModelSendConfigExample example);

    int updateByExample(@Param("record") OpsPoModelSendConfig record, @Param("example") OpsPoModelSendConfigExample example);
}