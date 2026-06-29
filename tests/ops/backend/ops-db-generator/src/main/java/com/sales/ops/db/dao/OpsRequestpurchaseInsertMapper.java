package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRequestpurchaseInsert;
import com.sales.ops.db.entity.OpsRequestpurchaseInsertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRequestpurchaseInsertMapper {
    long countByExample(OpsRequestpurchaseInsertExample example);

    int deleteByExample(OpsRequestpurchaseInsertExample example);

    int insert(OpsRequestpurchaseInsert record);

    int insertSelective(OpsRequestpurchaseInsert record);

    List<OpsRequestpurchaseInsert> selectByExample(OpsRequestpurchaseInsertExample example);

    int updateByExampleSelective(@Param("record") OpsRequestpurchaseInsert record, @Param("example") OpsRequestpurchaseInsertExample example);

    int updateByExample(@Param("record") OpsRequestpurchaseInsert record, @Param("example") OpsRequestpurchaseInsertExample example);
}