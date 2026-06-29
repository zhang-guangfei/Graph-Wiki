package com.sales.ops.db.dao;

import com.sales.ops.db.entity.StockAdjust;
import com.sales.ops.db.entity.StockAdjustExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockAdjustMapper {
    long countByExample(StockAdjustExample example);

    int deleteByExample(StockAdjustExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StockAdjust record);

    int insertSelective(StockAdjust record);

    List<StockAdjust> selectByExample(StockAdjustExample example);

    StockAdjust selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StockAdjust record, @Param("example") StockAdjustExample example);

    int updateByExample(@Param("record") StockAdjust record, @Param("example") StockAdjustExample example);

    int updateByPrimaryKeySelective(StockAdjust record);

    int updateByPrimaryKey(StockAdjust record);
}