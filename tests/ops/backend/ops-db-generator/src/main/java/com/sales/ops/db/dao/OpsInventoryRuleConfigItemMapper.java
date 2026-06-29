package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryRuleConfigItem;
import com.sales.ops.db.entity.OpsInventoryRuleConfigItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryRuleConfigItemMapper {
    long countByExample(OpsInventoryRuleConfigItemExample example);

    int deleteByExample(OpsInventoryRuleConfigItemExample example);

    int deleteByPrimaryKey(Integer inventoryRuleConfigItemId);

    int insert(OpsInventoryRuleConfigItem record);

    int insertSelective(OpsInventoryRuleConfigItem record);

    List<OpsInventoryRuleConfigItem> selectByExample(OpsInventoryRuleConfigItemExample example);

    OpsInventoryRuleConfigItem selectByPrimaryKey(Integer inventoryRuleConfigItemId);

    int updateByExampleSelective(@Param("record") OpsInventoryRuleConfigItem record, @Param("example") OpsInventoryRuleConfigItemExample example);

    int updateByExample(@Param("record") OpsInventoryRuleConfigItem record, @Param("example") OpsInventoryRuleConfigItemExample example);

    int updateByPrimaryKeySelective(OpsInventoryRuleConfigItem record);

    int updateByPrimaryKey(OpsInventoryRuleConfigItem record);
}