package com.sales.ops.db.dao;

import com.sales.ops.db.entity.StockAnalysis;
import com.sales.ops.db.entity.StockAnalysisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockAnalysisMapper {
    long countByExample(StockAnalysisExample example);

    int deleteByExample(StockAnalysisExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StockAnalysis record);

    int insertSelective(StockAnalysis record);

    List<StockAnalysis> selectByExample(StockAnalysisExample example);

    StockAnalysis selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") StockAnalysis record, @Param("example") StockAnalysisExample example);

    int updateByExample(@Param("record") StockAnalysis record, @Param("example") StockAnalysisExample example);

    int updateByPrimaryKeySelective(StockAnalysis record);

    int updateByPrimaryKey(StockAnalysis record);
}