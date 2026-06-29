package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDeliveryPlanResult;
import com.sales.ops.db.entity.OpsDeliveryPlanResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDeliveryPlanResultMapper {
    long countByExample(OpsDeliveryPlanResultExample example);

    int deleteByExample(OpsDeliveryPlanResultExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDeliveryPlanResult record);

    int insertSelective(OpsDeliveryPlanResult record);

    List<OpsDeliveryPlanResult> selectByExample(OpsDeliveryPlanResultExample example);

    OpsDeliveryPlanResult selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDeliveryPlanResult record, @Param("example") OpsDeliveryPlanResultExample example);

    int updateByExample(@Param("record") OpsDeliveryPlanResult record, @Param("example") OpsDeliveryPlanResultExample example);

    int updateByPrimaryKeySelective(OpsDeliveryPlanResult record);

    int updateByPrimaryKey(OpsDeliveryPlanResult record);
}