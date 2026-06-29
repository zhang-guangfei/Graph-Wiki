package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchaseErrorLog;
import com.sales.ops.db.entity.OpsPurchaseErrorLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsPurchaseErrorLogMapper {
    long countByExample(OpsPurchaseErrorLogExample example);

    int deleteByExample(OpsPurchaseErrorLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPurchaseErrorLog record);

    int insertSelective(OpsPurchaseErrorLog record);

    List<OpsPurchaseErrorLog> selectByExample(OpsPurchaseErrorLogExample example);

    OpsPurchaseErrorLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPurchaseErrorLog record, @Param("example") OpsPurchaseErrorLogExample example);

    int updateByExample(@Param("record") OpsPurchaseErrorLog record, @Param("example") OpsPurchaseErrorLogExample example);

    int updateByPrimaryKeySelective(OpsPurchaseErrorLog record);

    int updateByPrimaryKey(OpsPurchaseErrorLog record);
}