package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPcoPost;
import com.sales.ops.db.entity.OpsPcoPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPcoPostMapper {
    long countByExample(OpsPcoPostExample example);

    int deleteByExample(OpsPcoPostExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPcoPost record);

    int insertSelective(OpsPcoPost record);

    List<OpsPcoPost> selectByExample(OpsPcoPostExample example);

    OpsPcoPost selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPcoPost record, @Param("example") OpsPcoPostExample example);

    int updateByExample(@Param("record") OpsPcoPost record, @Param("example") OpsPcoPostExample example);

    int updateByPrimaryKeySelective(OpsPcoPost record);

    int updateByPrimaryKey(OpsPcoPost record);
}