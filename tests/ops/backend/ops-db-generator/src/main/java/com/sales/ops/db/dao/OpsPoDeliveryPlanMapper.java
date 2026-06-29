package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoDeliveryPlan;
import com.sales.ops.db.entity.OpsPoDeliveryPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoDeliveryPlanMapper {
    long countByExample(OpsPoDeliveryPlanExample example);

    int deleteByExample(OpsPoDeliveryPlanExample example);

    int insert(OpsPoDeliveryPlan record);

    int insertSelective(OpsPoDeliveryPlan record);

    List<OpsPoDeliveryPlan> selectByExample(OpsPoDeliveryPlanExample example);

    int updateByExampleSelective(@Param("record") OpsPoDeliveryPlan record, @Param("example") OpsPoDeliveryPlanExample example);

    int updateByExample(@Param("record") OpsPoDeliveryPlan record, @Param("example") OpsPoDeliveryPlanExample example);
}