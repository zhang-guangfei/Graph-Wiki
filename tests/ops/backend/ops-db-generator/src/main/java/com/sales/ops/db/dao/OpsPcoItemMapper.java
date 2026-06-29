package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.db.entity.OpsPcoItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPcoItemMapper {
    long countByExample(OpsPcoItemExample example);

    int deleteByExample(OpsPcoItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPcoItem record);

    int insertSelective(OpsPcoItem record);

    List<OpsPcoItem> selectByExample(OpsPcoItemExample example);

    OpsPcoItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPcoItem record, @Param("example") OpsPcoItemExample example);

    int updateByExample(@Param("record") OpsPcoItem record, @Param("example") OpsPcoItemExample example);

    int updateByPrimaryKeySelective(OpsPcoItem record);

    int updateByPrimaryKey(OpsPcoItem record);
}