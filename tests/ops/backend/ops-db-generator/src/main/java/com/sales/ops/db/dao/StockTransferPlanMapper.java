package com.sales.ops.db.dao;

import com.sales.ops.db.entity.StockTransferPlan;
import com.sales.ops.db.entity.StockTransferPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockTransferPlanMapper {
    long countByExample(StockTransferPlanExample example);

    int deleteByExample(StockTransferPlanExample example);

    int insert(StockTransferPlan record);

    int insertSelective(StockTransferPlan record);

    List<StockTransferPlan> selectByExample(StockTransferPlanExample example);

    int updateByExampleSelective(@Param("record") StockTransferPlan record, @Param("example") StockTransferPlanExample example);

    int updateByExample(@Param("record") StockTransferPlan record, @Param("example") StockTransferPlanExample example);
}