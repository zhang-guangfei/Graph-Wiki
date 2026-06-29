package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDeliveryPlanDetail;
import com.sales.ops.db.entity.OpsDeliveryPlanDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDeliveryPlanDetailMapper {
    long countByExample(OpsDeliveryPlanDetailExample example);

    int deleteByExample(OpsDeliveryPlanDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDeliveryPlanDetail record);

    int insertSelective(OpsDeliveryPlanDetail record);

    List<OpsDeliveryPlanDetail> selectByExample(OpsDeliveryPlanDetailExample example);

    OpsDeliveryPlanDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDeliveryPlanDetail record, @Param("example") OpsDeliveryPlanDetailExample example);

    int updateByExample(@Param("record") OpsDeliveryPlanDetail record, @Param("example") OpsDeliveryPlanDetailExample example);

    int updateByPrimaryKeySelective(OpsDeliveryPlanDetail record);

    int updateByPrimaryKey(OpsDeliveryPlanDetail record);
}