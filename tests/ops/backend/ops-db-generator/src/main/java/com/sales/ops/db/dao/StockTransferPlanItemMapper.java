package com.sales.ops.db.dao;

import com.sales.ops.db.entity.StockTransferPlanItem;
import com.sales.ops.db.entity.StockTransferPlanItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockTransferPlanItemMapper {
    long countByExample(StockTransferPlanItemExample example);

    int deleteByExample(StockTransferPlanItemExample example);

    int insert(StockTransferPlanItem record);

    int insertSelective(StockTransferPlanItem record);

    List<StockTransferPlanItem> selectByExample(StockTransferPlanItemExample example);

    int updateByExampleSelective(@Param("record") StockTransferPlanItem record, @Param("example") StockTransferPlanItemExample example);

    int updateByExample(@Param("record") StockTransferPlanItem record, @Param("example") StockTransferPlanItemExample example);
}