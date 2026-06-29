package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDeliveryEstimateResult;
import com.sales.ops.db.entity.OpsDeliveryEstimateResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDeliveryEstimateResultMapper {
    long countByExample(OpsDeliveryEstimateResultExample example);

    int deleteByExample(OpsDeliveryEstimateResultExample example);

    int insert(OpsDeliveryEstimateResult record);

    int insertSelective(OpsDeliveryEstimateResult record);

    List<OpsDeliveryEstimateResult> selectByExample(OpsDeliveryEstimateResultExample example);

    int updateByExampleSelective(@Param("record") OpsDeliveryEstimateResult record, @Param("example") OpsDeliveryEstimateResultExample example);

    int updateByExample(@Param("record") OpsDeliveryEstimateResult record, @Param("example") OpsDeliveryEstimateResultExample example);
}