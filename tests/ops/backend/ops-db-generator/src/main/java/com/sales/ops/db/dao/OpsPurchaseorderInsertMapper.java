package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchaseorderInsert;
import com.sales.ops.db.entity.OpsPurchaseorderInsertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchaseorderInsertMapper {
    long countByExample(OpsPurchaseorderInsertExample example);

    int deleteByExample(OpsPurchaseorderInsertExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPurchaseorderInsert record);

    int insertSelective(OpsPurchaseorderInsert record);

    List<OpsPurchaseorderInsert> selectByExample(OpsPurchaseorderInsertExample example);

    OpsPurchaseorderInsert selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPurchaseorderInsert record, @Param("example") OpsPurchaseorderInsertExample example);

    int updateByExample(@Param("record") OpsPurchaseorderInsert record, @Param("example") OpsPurchaseorderInsertExample example);

    int updateByPrimaryKeySelective(OpsPurchaseorderInsert record);

    int updateByPrimaryKey(OpsPurchaseorderInsert record);
}