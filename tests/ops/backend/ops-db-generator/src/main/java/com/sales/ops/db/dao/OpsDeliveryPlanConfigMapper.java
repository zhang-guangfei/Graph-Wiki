package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDeliveryPlanConfig;
import com.sales.ops.db.entity.OpsDeliveryPlanConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDeliveryPlanConfigMapper {
    long countByExample(OpsDeliveryPlanConfigExample example);

    int deleteByExample(OpsDeliveryPlanConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDeliveryPlanConfig record);

    int insertSelective(OpsDeliveryPlanConfig record);

    List<OpsDeliveryPlanConfig> selectByExample(OpsDeliveryPlanConfigExample example);

    OpsDeliveryPlanConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDeliveryPlanConfig record, @Param("example") OpsDeliveryPlanConfigExample example);

    int updateByExample(@Param("record") OpsDeliveryPlanConfig record, @Param("example") OpsDeliveryPlanConfigExample example);

    int updateByPrimaryKeySelective(OpsDeliveryPlanConfig record);

    int updateByPrimaryKey(OpsDeliveryPlanConfig record);
}