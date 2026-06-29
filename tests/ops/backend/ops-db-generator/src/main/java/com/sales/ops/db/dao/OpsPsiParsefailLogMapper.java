package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPsiParsefailLog;
import com.sales.ops.db.entity.OpsPsiParsefailLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsPsiParsefailLogMapper {
    long countByExample(OpsPsiParsefailLogExample example);

    int deleteByExample(OpsPsiParsefailLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPsiParsefailLog record);

    int insertSelective(OpsPsiParsefailLog record);

    List<OpsPsiParsefailLog> selectByExample(OpsPsiParsefailLogExample example);

    OpsPsiParsefailLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPsiParsefailLog record, @Param("example") OpsPsiParsefailLogExample example);

    int updateByExample(@Param("record") OpsPsiParsefailLog record, @Param("example") OpsPsiParsefailLogExample example);

    int updateByPrimaryKeySelective(OpsPsiParsefailLog record);

    int updateByPrimaryKey(OpsPsiParsefailLog record);
}