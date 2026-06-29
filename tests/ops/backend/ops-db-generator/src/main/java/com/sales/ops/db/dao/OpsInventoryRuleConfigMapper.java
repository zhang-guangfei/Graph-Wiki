package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryRuleConfig;
import com.sales.ops.db.entity.OpsInventoryRuleConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryRuleConfigMapper {
    long countByExample(OpsInventoryRuleConfigExample example);

    int deleteByExample(OpsInventoryRuleConfigExample example);

    int deleteByPrimaryKey(String ruleCode);

    int insert(OpsInventoryRuleConfig record);

    int insertSelective(OpsInventoryRuleConfig record);

    List<OpsInventoryRuleConfig> selectByExample(OpsInventoryRuleConfigExample example);

    OpsInventoryRuleConfig selectByPrimaryKey(String ruleCode);

    int updateByExampleSelective(@Param("record") OpsInventoryRuleConfig record, @Param("example") OpsInventoryRuleConfigExample example);

    int updateByExample(@Param("record") OpsInventoryRuleConfig record, @Param("example") OpsInventoryRuleConfigExample example);

    int updateByPrimaryKeySelective(OpsInventoryRuleConfig record);

    int updateByPrimaryKey(OpsInventoryRuleConfig record);
}