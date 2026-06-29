package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDeliveryPlanReliabilityConfig;
import com.sales.ops.db.entity.OpsDeliveryPlanReliabilityConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsDeliveryPlanReliabilityConfigMapper {
    long countByExample(OpsDeliveryPlanReliabilityConfigExample example);

    int deleteByExample(OpsDeliveryPlanReliabilityConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDeliveryPlanReliabilityConfig record);

    int insertSelective(OpsDeliveryPlanReliabilityConfig record);

    List<OpsDeliveryPlanReliabilityConfig> selectByExample(OpsDeliveryPlanReliabilityConfigExample example);

    OpsDeliveryPlanReliabilityConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDeliveryPlanReliabilityConfig record, @Param("example") OpsDeliveryPlanReliabilityConfigExample example);

    int updateByExample(@Param("record") OpsDeliveryPlanReliabilityConfig record, @Param("example") OpsDeliveryPlanReliabilityConfigExample example);

    int updateByPrimaryKeySelective(OpsDeliveryPlanReliabilityConfig record);

    int updateByPrimaryKey(OpsDeliveryPlanReliabilityConfig record);
}