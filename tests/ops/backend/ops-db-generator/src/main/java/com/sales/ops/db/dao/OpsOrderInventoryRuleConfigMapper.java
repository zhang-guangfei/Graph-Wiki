package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsOrderInventoryRuleConfig;
import com.sales.ops.db.entity.OpsOrderInventoryRuleConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsOrderInventoryRuleConfigMapper {
    long countByExample(OpsOrderInventoryRuleConfigExample example);

    int deleteByExample(OpsOrderInventoryRuleConfigExample example);

    int deleteByPrimaryKey(Long orderInventoryRuleConfigId);

    int insert(OpsOrderInventoryRuleConfig record);

    int insertSelective(OpsOrderInventoryRuleConfig record);

    List<OpsOrderInventoryRuleConfig> selectByExample(OpsOrderInventoryRuleConfigExample example);

    OpsOrderInventoryRuleConfig selectByPrimaryKey(Long orderInventoryRuleConfigId);

    int updateByExampleSelective(@Param("record") OpsOrderInventoryRuleConfig record, @Param("example") OpsOrderInventoryRuleConfigExample example);

    int updateByExample(@Param("record") OpsOrderInventoryRuleConfig record, @Param("example") OpsOrderInventoryRuleConfigExample example);

    int updateByPrimaryKeySelective(OpsOrderInventoryRuleConfig record);

    int updateByPrimaryKey(OpsOrderInventoryRuleConfig record);
}