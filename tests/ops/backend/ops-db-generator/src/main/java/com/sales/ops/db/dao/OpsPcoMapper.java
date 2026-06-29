package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsPcoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPcoMapper {
    long countByExample(OpsPcoExample example);

    int deleteByExample(OpsPcoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPco record);

    int insertSelective(OpsPco record);

    List<OpsPco> selectByExample(OpsPcoExample example);

    OpsPco selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPco record, @Param("example") OpsPcoExample example);

    int updateByExample(@Param("record") OpsPco record, @Param("example") OpsPcoExample example);

    int updateByPrimaryKeySelective(OpsPco record);

    int updateByPrimaryKey(OpsPco record);
}