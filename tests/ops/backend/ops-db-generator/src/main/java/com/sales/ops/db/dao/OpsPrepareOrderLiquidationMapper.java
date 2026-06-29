package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPrepareOrderLiquidation;
import com.sales.ops.db.entity.OpsPrepareOrderLiquidationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsPrepareOrderLiquidationMapper {
    long countByExample(OpsPrepareOrderLiquidationExample example);

    int deleteByExample(OpsPrepareOrderLiquidationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPrepareOrderLiquidation record);

    int insertSelective(OpsPrepareOrderLiquidation record);

    List<OpsPrepareOrderLiquidation> selectByExample(OpsPrepareOrderLiquidationExample example);

    OpsPrepareOrderLiquidation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPrepareOrderLiquidation record, @Param("example") OpsPrepareOrderLiquidationExample example);

    int updateByExample(@Param("record") OpsPrepareOrderLiquidation record, @Param("example") OpsPrepareOrderLiquidationExample example);

    int updateByPrimaryKeySelective(OpsPrepareOrderLiquidation record);

    int updateByPrimaryKey(OpsPrepareOrderLiquidation record);
}