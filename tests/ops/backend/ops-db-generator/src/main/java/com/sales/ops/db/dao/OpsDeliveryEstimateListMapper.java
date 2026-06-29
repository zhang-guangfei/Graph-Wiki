package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDeliveryEstimateList;
import com.sales.ops.db.entity.OpsDeliveryEstimateListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDeliveryEstimateListMapper {
    long countByExample(OpsDeliveryEstimateListExample example);

    int deleteByExample(OpsDeliveryEstimateListExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDeliveryEstimateList record);

    int insertSelective(OpsDeliveryEstimateList record);

    List<OpsDeliveryEstimateList> selectByExample(OpsDeliveryEstimateListExample example);

    OpsDeliveryEstimateList selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDeliveryEstimateList record, @Param("example") OpsDeliveryEstimateListExample example);

    int updateByExample(@Param("record") OpsDeliveryEstimateList record, @Param("example") OpsDeliveryEstimateListExample example);

    int updateByPrimaryKeySelective(OpsDeliveryEstimateList record);

    int updateByPrimaryKey(OpsDeliveryEstimateList record);
}